package com.example.developster.domain.notification.service;

import com.example.developster.domain.notification.dto.response.NotificationResponseDto;
import com.example.developster.domain.notification.dto.request.CreateNotificationRequest;
import com.example.developster.domain.notification.entity.Notification;
import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.notification.repository.EmitterRepository;
import com.example.developster.domain.notification.repository.NotificationRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.domain.user.main.repository.UserRepository;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;
    private final UserRepository userRepository;

    public SseEmitter subscribe(User user, String lastEventId) {
        Long userId = user.getId();
        String emitterId = makeTimeIncludeId(userId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> {
            emitter.complete();
            emitterRepository.deleteById(emitterId);
        });

        // 첫 연결 시 503 Service Unavailable 방지용 더미 Event 전송
        sendToClient(emitter, emitterId, "알림 서버 연결 성공. [userId=" + userId + "]");

        if (!lastEventId.isEmpty()) {
            sendLostData(lastEventId, userId, emitter);
        }

        return emitter;
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            emitter.completeWithError(exception);
        }
    }

    private void sendLostData(String lastEventId, Long userId, SseEmitter emitter) {
        // eventCache에서 해당 memberId로 시작하는 이벤트들 가져오기
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
        // lastEventId 이후의 이벤트만 필터링
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0) // lastEventId 이후의 이벤트만
                .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue())); // 필터링된 이벤트를 클라이언트로 전송
    }

    @Transactional
    public void sendNotificationToAllUsers(User loginMember, CreateNotificationRequest request) {
        validateAdmin(loginMember);
        List<User> allMembers = userRepository.findAllByStatus(User.Status.ACTIVE);
        allMembers.forEach(member -> sendAsync(
                member, request.notificationType(), request.content(), request.referenceId()));
    }

    private void validateAdmin(User loginMember) {
        if (loginMember.getRole() != User.Role.ROLE_ADMIN) {
            throw new InvalidParamException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendAsync(User recipient, NotificationType notificationType, String content, Long referenceId) {
        Notification newNotification = Notification.builder()
                .recipient(recipient)
                .message(content)
                .referenceId(referenceId)
                .type(notificationType)
                .build();

        notificationRepository.save(newNotification);
        log.info("Thread: {}, Notification sent to user: {}, type: {}, content: {}, reference_id: {}",
                Thread.currentThread().getName(), recipient.getId(), notificationType, content, referenceId);
    }
    private String makeTimeIncludeId(Long userId) { // (3)
        return userId + "_" + System.currentTimeMillis();
    }

    public void sendNotification(User recipient, User sender, String message, Long referenceId, NotificationType type) {
        Notification newNotification = Notification.builder()
                .recipient(recipient)
                .sender(sender)
                .message(message)
                .referenceId(referenceId)
                .type(type)
                .build();

        Long recipientId = recipient.getId();

        Notification save = notificationRepository.save(newNotification);

        NotificationResponseDto dto = NotificationResponseDto.toDto(save);
        String eventId = makeTimeIncludeId(recipientId);


//
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(recipientId));
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, dto);
                    sendToClient(emitter, eventId,
                            dto);
                }
        );
    }

    // 전체 알림 조회
    public List<NotificationResponseDto> findAllNotification() {
        return notificationRepository.findAll().stream().map(NotificationResponseDto::toDto).toList();
    }

    // 전체 알림 일음 처리
    public void allReadNotifications(Long id) {

        List<Notification> allNotification = notificationRepository.findAll();

        for (Notification notification : allNotification) {
            if (notification.getId().equals(id)) {
                notification.setIsRead(true);
            }
        }
    }

    // 특정 알림 읽음 처리
    public void readNotificationById(Long id, Long userId) {
        Notification notification = notificationRepository.findByIdOrElseThrow(id);
        notification.validateNotificationRecipient(userId);
        notification.setIsRead(true);
    }

    // 특정 알림 삭제
    public void deleteNotification(Long id, Long userId) {

        Notification notification = notificationRepository.findByIdOrElseThrow(id);
        notification.validateNotificationRecipient(userId);

        notificationRepository.deleteById(id);
        emitterRepository.deleteEventCache(userId + "_" + id);
    }

    // 전체 알림 삭제
    public void deleteAllNotification(Long userId) {
        notificationRepository.deleteAllByRecipientId(userId);
    }


}
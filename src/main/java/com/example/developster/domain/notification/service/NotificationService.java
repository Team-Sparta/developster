package com.example.developster.domain.notification.service;

import com.example.developster.domain.notification.dto.NotificationResponseDto;
import com.example.developster.domain.notification.entity.Notification;
import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.notification.repository.NotificationRepository;
import com.example.developster.domain.post.like.repository.PostLikeJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // 전체 알림 조회
//    public List<NotificationResponseDto> findAllNotification() {
//
//        return notificationRepository.findAll().stream().map(NotificationResponseDto::toDto).toList();
//    }

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
    }

    // 전체 알림 삭제
    public void deleteAllNotification(Long userId) {
        notificationRepository.deleteAllByRecipientId(userId);
    }


    public void sendNotification(User recipient, User sender, Long referenceId, String message, NotificationType type) {
        Notification newNotification = Notification.builder()
                .recipient(recipient)
                .sender(sender)
                .message(message)
                .referenceId(referenceId)
                .type(type)
                .build();

        notificationRepository.save(newNotification);
    }
}
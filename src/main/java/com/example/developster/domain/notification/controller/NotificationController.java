package com.example.developster.domain.notification.controller;

import com.example.developster.domain.notification.dto.NotificationRequestDto;
import com.example.developster.domain.notification.dto.NotificationResponseDto;
import com.example.developster.domain.notification.dto.request.CreateNotificationRequest;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(
            summary = "알림 구독",
            description = "알림을 위한 SSE 구독"
    )
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user,
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId
    ) {
        return notificationService.subscribe(user, lastEventId);
    }

    @Operation(
            summary = "전체 사용자 알림 전송",
            description = "관리자가 모든 사용자에게 알림 메시지를 전송합니다."
    )
    @PostMapping("/users")
    public ResponseEntity<CommonResponse<Void>> sendToAllUsers(
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user,
            @RequestBody @Valid CreateNotificationRequest request
    ) {
        notificationService.sendNotificationToAllUsers(user, request);
        return CommonResponse.success(SuccessCode.SUCCESS);
    }

    // 전체 알림 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<NotificationResponseDto>>> findAllNotifications() {
        List<NotificationResponseDto> allNotification = notificationService.findAllNotification();

        return CommonResponse.success(SuccessCode.SUCCESS, allNotification);
    }

    // 전체 알림 읽음 처리
    @PostMapping("/{notificationId}/read-all")
    public ResponseEntity<CommonResponse<Void>> allReadNotifications(
            @PathVariable Long notificationId
    ) {
        notificationService.allReadNotifications(notificationId);

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE);
    }

    // 특정 알림 읽음 처리
    @PostMapping("/{notificationId}/read")
    public ResponseEntity<CommonResponse<Void>> readNotificationById(
            @PathVariable Long notificationId,
            @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        notificationService.readNotificationById(notificationId, user.getId());

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE);
    }

    // 특정 알림 삭제
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<CommonResponse<Void>> deleteNotification(
            @PathVariable Long notificationId,
            @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        notificationService.deleteNotification(notificationId, user.getId());

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }

    // 전체 알림 삭제
    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteAllNotifications(
            @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {

        notificationService.deleteAllNotification(user.getId());

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }
}

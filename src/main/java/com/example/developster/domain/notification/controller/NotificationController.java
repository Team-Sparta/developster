package com.example.developster.domain.notification.controller;

import com.example.developster.domain.notification.dto.NotificationRequestDto;
import com.example.developster.domain.notification.dto.NotificationResponseDto;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

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
    public ResponseEntity<CommonResponse<Void>> deleteAllNotifications() {

        notificationService.deleteAllNotification();

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }
}

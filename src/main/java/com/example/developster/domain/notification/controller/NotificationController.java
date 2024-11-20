package com.example.developster.domain.notification.controller;

import com.example.developster.domain.notification.dto.NotificationRequestDto;
import com.example.developster.domain.notification.dto.NotificationResponseDto;
import com.example.developster.domain.notification.service.NotificationService;
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

    @GetMapping
    public ResponseEntity<CommonResponse<List<NotificationResponseDto>>> findAllNotifications() {

        List<NotificationResponseDto> allNotification = notificationService.findAllNotification();

        return CommonResponse.success(SuccessCode.SUCCESS, allNotification);
    }

    @PostMapping("/read-all")
    public ResponseEntity<CommonResponse<Void>> allReadNotifications(Boolean isRead) {

        notificationService.allReadNotifications(isRead);

        return CommonResponse.success(SuccessCode.SUCCESS);
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<CommonResponse<Void>> readNotificationById(
            @PathVariable Long notificationId,
            @RequestBody NotificationRequestDto RequestDto
    ) {

        notificationService.readNotificationById(notificationId, RequestDto.getIsRead());

        return CommonResponse.success(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<CommonResponse<Void>> deleteNotification(@PathVariable Long notificationId) {

        notificationService.deleteNotification(notificationId);

        return CommonResponse.success(SuccessCode.SUCCESS);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteAllNotifications() {

        notificationService.deleteAllNotification();

        return CommonResponse.success(SuccessCode.SUCCESS);
    }

}

package com.example.developster.domain.notification.controller;

import com.example.developster.domain.notification.dto.response.NotificationResponseDto;
import com.example.developster.domain.notification.dto.request.CreateNotificationRequest;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Tag(
        name = "알림 API",
        description = "알림 관련 API"
)
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

    @Operation(
            summary = "알림 목록 조회",
            description = "사용자의 모든 알림 메시지를 조회합니다."
    )
    @GetMapping
    public ResponseEntity<CommonResponse<List<NotificationResponseDto>>> findAllNotifications() {
        List<NotificationResponseDto> allNotification = notificationService.findAllNotification();

        return CommonResponse.success(SuccessCode.SUCCESS, allNotification);
    }

    @Operation(
            summary = "모든 알림 읽음 처리",
            description = "사용자의 모든 알림을 읽음 처리 합니다."
    )
    @PostMapping("/{notificationId}/read-all")
    public ResponseEntity<CommonResponse<Void>> allReadNotifications(
            @PathVariable Long notificationId
    ) {
        notificationService.allReadNotifications(notificationId);

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE);
    }

    @Operation(
            summary = "알림 읽음 처리",
            description = "사용자가 클릭한 알림을 읽음 처리 합니다."
    )
    @PostMapping("/{notificationId}/read")
    public ResponseEntity<CommonResponse<Void>> readNotificationById(
            @PathVariable Long notificationId,
            @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        notificationService.readNotificationById(notificationId, user.getId());

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE);
    }

    @Operation(
            summary = "알림 삭제",
            description = "사용자가 선택한 알림을 삭제합니다."
    )
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<CommonResponse<Void>> deleteNotification(
            @PathVariable Long notificationId,
            @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        notificationService.deleteNotification(notificationId, user.getId());

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }

    @Operation(
            summary = "모든 알림 삭제",
            description = "사용자의 모든 알림을 삭제합니다."
    )
    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteAllNotifications(
            @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {

        notificationService.deleteAllNotification(user.getId());

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }
}

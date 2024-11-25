package com.example.developster.domain.notification.entity;

import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseCreatedTimeEntity;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "notifications")
@Getter
public class Notification extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment '알림 고유 번호'")
    Long id;

    @Column(name = "message", nullable = false)
    String message;

    @Setter
    @Column(name = "is_read", nullable = false)
    Boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false, columnDefinition = "BIGINT UNSIGNED comment '받는자의 회원 고유 번호'")
    User recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id", columnDefinition = "BIGINT UNSIGNED comment '발송자 회원 고유 번호'")
    User sender;

    @Column(name = "alert_type")
    @Enumerated(EnumType.STRING)
    NotificationType type;

    @Column(name = "reference_id")
    Long reference_id;


    @Builder
    public Notification(Long id, @NotBlank String message, @NotNull User recipient, User sender, @NotNull NotificationType type, @NotNull Long referenceId) {
        this.id = id;
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
        this.type = type;
        this.reference_id = referenceId;
    }

    public void validateNotificationRecipient(Long userId) {
        if (!userId.equals(this.recipient.getId())) {
            throw new InvalidParamException(ErrorCode.NOT_POST_WRITER);
        }
    }
}

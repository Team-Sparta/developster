package com.example.developster.domain.notification.entity;

import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseCreatedTimeEntity;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
public class Notification extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "message", nullable = false)
    String message;

    @Setter
    @Column(name = "is_read", nullable = false)
    Boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "alert_type")
    @Enumerated(EnumType.STRING)
    NotificationType type;

    @Column(name = "related_url", nullable = false)
    String relatedUrl;

    public Notification() {}

    public void validateScheduleWriter(Long userId) {
        if (!userId.equals(this.user.getId())) {
            throw new InvalidParamException(ErrorCode.NOT_POST_WRITER);
        }
    }

    @Builder
    public Notification(Long id, @NotNull String message, @NotNull User user, @NotNull NotificationType type, @NotNull String relatedUrl) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.type = type;
        this.relatedUrl = relatedUrl;
    }
}

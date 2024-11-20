package com.example.developster.domain.notification.entity;

import com.example.developster.domain.notification.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long notificationId;

    @Column(name = "message", nullable = false)
    String message;

    @Column(name = "is_read", nullable = false)
    Boolean isRead = false;

    @manyToOne
    @joinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "alert_type")
    NotificationType type;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "related_url", nullable = false)
    String relatedUrl;

    public Notification() {}

}

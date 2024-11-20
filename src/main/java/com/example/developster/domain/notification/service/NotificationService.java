package com.example.developster.domain.notification.service;

import com.example.developster.domain.notification.dto.NotificationResponseDto;
import com.example.developster.domain.notification.entity.Notification;
import com.example.developster.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;


    public List<NotificationResponseDto> findAllNotification() {

       return notificationRepository.findAll().stream().map(NotificationResponseDto::toDto).toList();
    }

    public void allReadNotifications(Boolean isRead) {

        List<Notification> allNotification = notificationRepository.findAll();

        for (Notification notification : allNotification) {
            if (notification.getIsRead()) {
                isRead = !isRead;
            }
        }
    }

    public void readNotificationById(Long id, Boolean isRead) {

        Notification notification = notificationRepository.findByIdOrElseThrow(id);

        if (notification.getIsRead()) {
            isRead = !isRead;
        }
    }

    public void deleteNotification(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 아이디값입니다." + id);
        }

        notificationRepository.deleteById(id);
    }

    public void deleteAllNotification() {

        notificationRepository.deleteAll();
    }
}

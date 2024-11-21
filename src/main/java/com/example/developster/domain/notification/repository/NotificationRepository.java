package com.example.developster.domain.notification.repository;

import com.example.developster.domain.notification.entity.Notification;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  default Notification findByIdOrElseThrow(Long id) {
      return findById(id).orElseThrow(()-> new InvalidParamException(ErrorCode.NOT_FOUND_MEMBER));
  }
    void deleteAllByRecipientId(Long recipientId);

}

package com.example.developster.domain.notification.repository;

import com.example.developster.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  default Notification findByIdOrElseThrow(Long id) {
      return findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 아이디값입니다." + id));
  }
}

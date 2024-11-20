package com.example.developster.domain.user.main.repository;
import com.example.developster.domain.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
}

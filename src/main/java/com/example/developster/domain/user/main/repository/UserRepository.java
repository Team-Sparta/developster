package com.example.developster.domain.user.main.repository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
     default User findByIdOrElseThrow(Long postId) {
          return findById(postId).orElseThrow(() -> new InvalidParamException(ErrorCode.NOT_FOUND_MEMBER));
     }

     User findByEmail(@NotNull String email);

     List<User> findAllByStatus(User.Status status);
}

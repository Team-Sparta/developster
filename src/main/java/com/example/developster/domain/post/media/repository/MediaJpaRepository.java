package com.example.developster.domain.post.media.repository;

import com.example.developster.domain.post.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaJpaRepository extends JpaRepository<Media, Long> {
    List<String> getUrlByPostId(Long postId);
}

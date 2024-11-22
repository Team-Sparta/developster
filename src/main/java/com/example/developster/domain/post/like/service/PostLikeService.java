package com.example.developster.domain.post.like.service;

import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.post.like.entity.PostLike;
import com.example.developster.domain.post.like.repository.PostLikeJpaRepository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.micrometer.common.util.StringUtils.truncate;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final NotificationService notificationService;

    @Transactional
    public void likePost(User user, Long postId) {
        Post post = postJpaRepository.fetchPost(postId);
        System.out.println("recipient_id: " + post.getUser().getId().toString());

        post.reverseValidatePostWriter(user.getId());

        PostLike postLike = postLikeJpaRepository.fetchOrCreatePostLike(user, post);

        if (postLike.getIsLiked()) {
            throw new BaseException(ErrorCode.ALREADY_LIKED_POST);
        }

        if (postLike.getCreatedAt() == null) {
            sendLikeNotification(user, post);
        }

        postLike.setIsLiked(true);
        postLikeJpaRepository.save(postLike);
    }

    @Transactional
    public void unlikePost(User user, Long postId) {
        Post post = postJpaRepository.fetchPost(postId);

        post.reverseValidatePostWriter(user.getId());

        Optional<PostLike> postLike = postLikeJpaRepository.findByUserAndPost(user, post);

        if (postLike.isEmpty() || postLike.get().getIsLiked()) {
            throw new BaseException(ErrorCode.NOT_FOUND_POST_LIKE);
        }
        postLike.get().setIsLiked(false);
    }

    /**
     * Sends a notification for a post like.
     */
    private void sendLikeNotification(User sender, Post post) {;

        System.out.println("sender_id: " + sender.getId().toString());

        String message = sender.getName() + "님이 " + truncate(post.getTitle(), 10) + "에 좋아요를 눌렀습니다.";
        notificationService.sendNotification(
                post.getUser(),
                sender,
                message,
                post.getId(),
                NotificationType.POST_LIKE
        );
    }
}

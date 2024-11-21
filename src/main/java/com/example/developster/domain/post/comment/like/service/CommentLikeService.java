package com.example.developster.domain.post.comment.like.service;

import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.post.comment.like.entity.CommentLike;
import com.example.developster.domain.post.comment.like.repository.CommentLikeRepository;
import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.post.comment.main.repository.CommentRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.truncate;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final NotificationService notificationService;

    @Transactional
    public void likeComment(Long commentId, User user) {
        //로그인 회원이 다른 사람의 댓글에 좋아요를 누른다.

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        //내 댓글에는 좋아요 못 누름!
        if(Objects.equals(comment.getUser().getId(), user.getId())){
            throw new BaseException(ErrorCode.CONSTRAINT_VIOLATION);
        }
        //이미 누른 경우에는 못 누름!
        Optional<CommentLike> optionalLike = likeRepository.findByComment_IdAndUser_Id(commentId, user.getId());
        if(optionalLike.isPresent()){
            CommentLike commentLike = optionalLike.get();
            if(commentLike.getIsLike()){
                throw new BaseException(ErrorCode.ALREADY_LIKED_COMMENT);
            }
            commentLike.setIsLike(true);
            return;
        }
        //좋아요 생성
        CommentLike like = CommentLike.builder()
                .user(user)
                .comment(comment)
                .build();

        likeRepository.save(like);
        sendLikeNotification(user,comment);
    }

    @Transactional
    public void unlikeComment(Long commentId, User user) {
        //로그인 한 회원이 다른 사람의 댓글에 좋아요 취소(삭제)
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        //내 댓글에는 취소 못누름!
        if(Objects.equals(comment.getUser().getId(), user.getId())){
            throw new BaseException(ErrorCode.CONSTRAINT_VIOLATION);
        }

        CommentLike commentLike = likeRepository.findByComment_IdAndUser_IdOrElseThrow(commentId, user.getId());
        commentLike.setIsLike(false);
    }
    private void sendLikeNotification(User liker, Comment comment) {
        String message = liker.getName() + "님이 " + truncate(comment.getContents(), 20) + "에 좋아요를 눌렀습니다.";
        notificationService.sendNotification(
                liker,
                comment.getUser(),
                comment.getId(),
                message,
                NotificationType.COMMENT_LIKE
        );
    }
}

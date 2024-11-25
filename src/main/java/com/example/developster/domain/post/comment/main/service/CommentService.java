package com.example.developster.domain.post.comment.main.service;

import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.post.comment.main.dto.*;
import com.example.developster.domain.post.comment.main.dto.request.CommentCreateRequestDto;
import com.example.developster.domain.post.comment.main.dto.response.CommentCreateResponseDto;
import com.example.developster.domain.post.comment.main.dto.response.CommentUpdateRequestDto;
import com.example.developster.domain.post.comment.main.dto.response.CommentUpdateResponseDto;
import com.example.developster.domain.post.comment.main.dto.summary.CommentSummariesDetailDto;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummariesDetailDto;
import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.post.comment.main.repository.CommentQueryRepository;
import com.example.developster.domain.post.comment.main.repository.CommentRepository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static io.micrometer.common.util.StringUtils.truncate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostJpaRepository postJpaRepository;
    private final NotificationService notificationService;
    private final CommentQueryRepository commentQueryRepository;

    public CommentCreateResponseDto createComment(CommentCreateRequestDto dto, Long postId, User loginUser) {
        //게시글 아이디에 맞는 게시글을 찾는다.
        Post post = postJpaRepository.fetchPost(postId);
        //dto의 parentId를 바탕으로 가져온 comment 세팅
        Long parentId = dto.getParentId();
        Comment comment = commentRepository.findByIdOrElseThrow(parentId);
        //코멘트를 생성한다.
        Comment newComment = Comment
                .builder()
                .user(loginUser)
                .post(post)
                .comment(comment)
                .contents(dto.getContents())
                .build();
        log.info("parent id: {}", parentId);
        Comment savedComment = commentRepository.save(newComment);
        log.info("user name: {}", savedComment.getUser().getName());
        sendCommentNotification(loginUser, post, newComment);
        return new CommentCreateResponseDto(savedComment);
    }

    public CommentSummariesDetailDto readComments(User user, Long postId, Long lastId, int size) {
        Slice<CommentDetailInfoDto> allComments = commentQueryRepository.getAllComments(user, lastId, size);
        return new CommentSummariesDetailDto(allComments);
    }

    public RepliesSummariesDetailDto readReplies(User user, Long commentId, Long lastId, int size) {
        Slice<CommentDetailInfoDto> allReplies = commentQueryRepository.getAllReplies(user, commentId, lastId, size);
        return new RepliesSummariesDetailDto(allReplies);
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, User loginUser, CommentUpdateRequestDto dto) {
        //요청을 보낸 유저가 작성한 코멘트가 맞는지 확인
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        comment.isValidAuthor(loginUser.getId());

        comment.setContents(dto.getContents());

        return new CommentUpdateResponseDto(comment.getId());
    }

    @Transactional
    public void deleteComment(Long commentId, User loginUser) {
        //요청을 보낸 유저가 작성한 코멘트가 맞는지 확인
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        comment.isValidAuthor(loginUser.getId());

        comment.delete();
    }

    private void sendCommentNotification(User sender, Post post, Comment comment) {
        String message = sender.getName() + "님이 " + truncate(post.getTitle(), 10) + "에 댓글을 달았습니다.";
        notificationService.sendNotification(
                comment.getUser(),
                sender,
                message,
                comment.getId(),
                NotificationType.COMMENT
        );
    }
}
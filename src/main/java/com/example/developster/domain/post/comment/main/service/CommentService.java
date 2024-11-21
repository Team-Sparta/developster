package com.example.developster.domain.post.comment.main.service;

import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.post.comment.main.dto.*;
import com.example.developster.domain.post.comment.main.dto.summary.CommentSummariesDetail;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummariesDetail;
import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.post.comment.main.repository.CommentRepository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static io.micrometer.common.util.StringUtils.truncate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostJpaRepository postJpaRepository;
    private final NotificationService notificationService;

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
        log.info("parent id: {}",parentId);
        Comment savedComment = commentRepository.save(newComment);
        sendLikeNotification(loginUser, post);
        return new CommentCreateResponseDto(savedComment);
    }

    public CommentSummariesDetail readComments(Long postId, Long lastId, int size) {
        Pageable pageable = PageRequest.of(0, size);

        boolean isFirst = (lastId == null || lastId == Long.MAX_VALUE);

        if (lastId == null) {
            lastId = Long.MAX_VALUE;
        }

        List<Comment> comments = commentRepository.readComments(postId, lastId, pageable);
        List<CommentReadResponseDto> dtoList = comments.stream().map(CommentReadResponseDto::new).toList();

        boolean isLast = comments.size() < size;

        return new CommentSummariesDetail(isFirst,isLast,size,dtoList);
    }

    public RepliesSummariesDetail readReplies(Long commentId, Long lastId, int size) {
        Pageable pageable = PageRequest.of(0,size);

        boolean isFirst = (lastId == null || lastId == Long.MAX_VALUE);

        if (lastId == null) {
            lastId = Long.MAX_VALUE;
        }

        List<Comment> comments = commentRepository.readReplies(commentId,lastId,pageable);
        List<CommentReadResponseDto> dtoList = comments.stream().map(CommentReadResponseDto::new).toList();

        boolean isLast = comments.size() < size;
        return new RepliesSummariesDetail(isFirst,isLast,size,dtoList);
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, User loginUser, CommentUpdateRequestDto dto) {
        //요청을 보낸 유저가 작성한 코멘트가 맞는지 확인
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        Long writerId = comment.getUser().getId();

        //틀리면
        if(!Objects.equals(writerId, loginUser.getId())){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        comment.setContents(dto.getContents());

        return new CommentUpdateResponseDto(comment.getId());
    }

    @Transactional
    public void deleteComment(Long commentId, User loginUser) {
        //요청을 보낸 유저가 작성한 코멘트가 맞는지 확인
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        Long writerId = comment.getUser().getId();

        //틀리면
        if(!Objects.equals(writerId, loginUser.getId())){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        comment.delete();
    }

    private void sendLikeNotification(User liker, Post post) {
        String message = liker.getName() + "님이 " + truncate(post.getTitle(), 10) + "에 댓글을 달았습니다.";
        notificationService.sendNotification(
                liker,
                post.getUser(),
                post.getId(),
                message,
                NotificationType.COMMENT
        );
    }
}

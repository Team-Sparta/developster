package com.example.developster.domain.post.comment.main.service;

import com.example.developster.domain.post.comment.main.dto.*;
import com.example.developster.domain.post.comment.main.dto.summary.CommentSummariesDetail;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummariesDetail;
import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.post.comment.main.repository.CommentRepository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentCreateResDto createComment(CommentCreateReqDto dto, Long postId, User loginUser) {
        //게시글 아이디에 맞는 게시글을 찾는다.
        Post post = new Post();
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

        Comment savedComment = commentRepository.save(newComment);

        return new CommentCreateResDto(savedComment);
    }

    public CommentSummariesDetail readComments(Long postId, Long lastId, int size) {
        Pageable pageable = PageRequest.of(0, size);

        boolean isFirst = (lastId == null || lastId == Long.MAX_VALUE);
        if (lastId == null) {
            lastId = Long.MAX_VALUE;
        }

        List<Comment> comments = commentRepository.readComments(postId, lastId, pageable);
        List<CommentReadResDto> dtoList = comments.stream().map(CommentReadResDto::new).toList();

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
        List<CommentReadResDto> dtoList = comments.stream().map(CommentReadResDto::new).toList();

        boolean isLast = comments.size() < size;
        return new RepliesSummariesDetail(isFirst,isLast,size,dtoList);
    }


    public CommentUpdateResDto updateComment(Long commentId, User loginUser, CommentUpdateReqDto dto) {
        //요청을 보낸 유저가 작성한 코멘트가 맞는지 확인
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        Long writerId = comment.getUser().getId();

        //틀리면
        if(!Objects.equals(writerId, loginUser.getId())){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        comment.setContents(dto.getContents());

        return new CommentUpdateResDto(comment.getId());
    }

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
}

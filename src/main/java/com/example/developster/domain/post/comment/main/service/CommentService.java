package com.example.developster.domain.post.comment.main.service;

import com.example.developster.domain.post.comment.main.dto.CommentUpdateReqDto;
import com.example.developster.domain.post.comment.main.dto.CommentUpdateResDto;
import com.example.developster.domain.post.comment.main.dto.summary.CommentSummariesDetail;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummariesDetail;
import com.example.developster.domain.post.comment.main.dto.CommentCreateReqDto;
import com.example.developster.domain.post.comment.main.dto.CommentCreateResDto;
import com.example.developster.domain.post.comment.main.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


    public CommentCreateResDto createComment(CommentCreateReqDto dto, Long postId, Long loginId) {
        return null;
    }

    public CommentSummariesDetail readComments(Long postId, Long lastId, int size) {
        return null;
    }

    public RepliesSummariesDetail readReplies(Long postId, Long commentId, Long lastId, int size) {
        return null;
    }

    public CommentUpdateResDto updateComment(Long commentId, Long loginId, CommentUpdateReqDto dto) {
        return null;
    }

    public void deleteComment(Long commentId, Long loginId) {

    }
}

package com.example.developster.domain.post.comment.main.repository;

import com.example.developster.domain.post.comment.like.entity.QCommentLike;
import com.example.developster.domain.post.comment.main.dto.CommentDetailInfo;
import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.post.comment.main.entity.QComment;
import com.example.developster.domain.post.main.entity.QPost;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.example.developster.domain.user.main.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QComment comment = QComment.comment;
    QCommentLike commentLike = QCommentLike.commentLike;
    QPost post = QPost.post;

    public Slice<CommentDetailInfo> getAllComments(User user, Long postId, int pageSize){
        List<CommentDetailInfo> commentDetailInfoList = jpaQueryFactory
                .select(
                        Projections.constructor(
                            CommentDetailInfo.class,
                            post.id,
                            comment.id,
                            comment.contents,
                            commentLike.count().as("likeCount"),
                            comment.count().as("commentCount"),
                            isLikedSubQuery(comment, user),
                            Projections.constructor(
                                    UserInfoDto.class,
                                    post.user.id,
                                    post.user.name,
                                    post.user.profile
                            )
                        )
                )
                .from(comment)
                .leftJoin()
    }

    private BooleanExpression isLikedSubQuery(QComment comment, User user) {
        return jpaQueryFactory
                .selectFrom(commentLike)
                .where(
                        commentLike.comment.eq(comment),
                        commentLike.user.eq(user),
                        commentLike.isLike.isTrue()
                )
                .exists();
    }
}

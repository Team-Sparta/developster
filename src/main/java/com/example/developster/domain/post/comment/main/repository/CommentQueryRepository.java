package com.example.developster.domain.post.comment.main.repository;

import com.example.developster.domain.post.comment.like.entity.QCommentLike;
import com.example.developster.domain.post.comment.main.dto.CommentDetailInfoDto;
import com.example.developster.domain.post.comment.main.entity.QComment;
import com.example.developster.domain.post.main.entity.QPost;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.example.developster.domain.user.main.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QComment comment = QComment.comment;
    QCommentLike commentLike = QCommentLike.commentLike;
    QPost post = QPost.post;

    public Slice<CommentDetailInfoDto> getAllComments (User user, Long lastCommentId, int pageSize){
        List<CommentDetailInfoDto> commentDetailInfoList = getQuery(user)
                .where(
                        comment.parentComment.isNull(),
                        post.deletedAt.isNull(),
                        noOffsetByCommentId(comment, lastCommentId)
                )
                .groupBy(comment.id)
                .orderBy(comment.createdAt.desc())
                .limit(pageSize+1L)
                .fetch();


        return getSlice(pageSize, commentDetailInfoList);
    }

    public Slice<CommentDetailInfoDto> getAllReplies (User user, Long commentId, Long lastCommentId, int pageSize){
        List<CommentDetailInfoDto> commentDetailInfoList = getQuery(user)
                .where(
                        comment.parentComment.id.eq(commentId),
                        post.deletedAt.isNull(),
                        noOffsetByCommentId(comment, lastCommentId)
                )
                .groupBy(comment.id)
                .orderBy(comment.createdAt.desc())
                .limit(pageSize+1L)
                .fetch();


        return getSlice(pageSize, commentDetailInfoList);
    }

    private JPAQuery<CommentDetailInfoDto> getQuery(User user) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                CommentDetailInfoDto.class,
                                post.id,
                                comment.id,
                                comment.contents,
                                commentLike.count().as("likeCount"),
                                comment.commentList.size().longValue().as("commentCount"),
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
                .leftJoin(post).on(comment.post.eq(post).and(comment.deletedAt.isNull()))
                .leftJoin(commentLike).on(commentLike.comment.eq(comment).and(commentLike.isLike.isTrue()));
    }

    private static SliceImpl<CommentDetailInfoDto> getSlice(int pageSize, List<CommentDetailInfoDto> commentDetailInfoList) {
        boolean hasNext = commentDetailInfoList.size() > pageSize;
        if (hasNext) {
            commentDetailInfoList.remove(pageSize);
        }
        return new SliceImpl<>(commentDetailInfoList, PageRequest.ofSize(pageSize), hasNext);
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
    private BooleanExpression noOffsetByCommentId(QComment comment, Long lastCommentId) {
        return (lastCommentId == null) ? null : comment.id.lt(lastCommentId);
    }
}
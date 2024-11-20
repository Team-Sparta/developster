package com.example.developster.domain.post.main.repository;

import com.example.developster.domain.post.comment.main.entity.QComment;
import com.example.developster.domain.post.like.entity.QPostLike;
import com.example.developster.domain.post.main.dto.PostDetailInfo;
import com.example.developster.domain.post.main.entity.QPost;
import com.example.developster.domain.post.main.enums.PostOrderType;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    QPost post = QPost.post;
    QPostLike postLike = QPostLike.postLike;
    QComment comment = QComment.comment;


    public PostDetailInfo getPostDetailById(Long postId, User user) {
        PostDetailInfo postDetailInfo = jpaQueryFactory
                .select(
                        Projections.constructor(
                                PostDetailInfo.class,
                                post.id,
                                post.title,
                                post.content,
                                Projections.constructor(
                                        UserInfoDto.class,
                                        post.user.id,
                                        post.user.name,
                                        post.user.profile
                                ),
                                post.createdAt,
                                postLike.count().as("likeCount"),
                                comment.count().as("commentCount"),
                                isLikedSubQuery(post, user)
                        )
                )
                .from(post)
                .leftJoin(comment).on(post.eq(comment.post))
                .leftJoin(postLike).on(postLike.post.eq(post))
                .where(
                        post.id.eq(postId),
                        isNotPrivateOrAuthor(post, user),
                        post.deletedAt.isNotNull()
                )
                .groupBy(post.id)
                .fetchOne();

        return Optional.ofNullable(postDetailInfo)
                .orElseThrow(() -> new InvalidParamException(ErrorCode.NOT_FOUND_POST));
    }

    public Slice<PostDetailInfo> getAllPosts(User user, Long lastPostId, int pageSize, PostOrderType orderType) {
        List<PostDetailInfo> postSummaryList = jpaQueryFactory
                .select(
                        Projections.constructor(
                                PostDetailInfo.class,
                                post.id,
                                post.title,
                                post.content,
                                Projections.constructor(
                                        UserInfoDto.class,
                                        post.user.id,
                                        post.user.name,
                                        post.user.profile
                                ),
                                post.createdAt,
                                postLike.count().as("likeCount"),
                                comment.count().as("commentCount"),
                                isLikedSubQuery(post, user),
                                post.isPrivate
                        )
                )
                .from(post)
                .leftJoin(postLike).on(postLike.post.eq(post))
                .leftJoin(comment).on(comment.post.eq(post))
                .where(
                        isNotPrivateOrAuthor(post, user),
                        post.deletedAt.isNotNull(),
                        noOffsetByPostId(post, lastPostId)
                )
                .groupBy(post.id)
                .orderBy(getOrderSpecifier(orderType))
                .limit(pageSize + 1L)
                .fetch();

        boolean hasNext = postSummaryList.size() > pageSize;
        if (hasNext) {
            postSummaryList.remove(pageSize);
        }
        return new SliceImpl<>(postSummaryList, PageRequest.ofSize(pageSize), hasNext);

    }

    private BooleanExpression noOffsetByPostId(QPost post, Long lastPostId) {
        return (lastPostId == null) ? null : post.id.lt(lastPostId);
    }

    private BooleanExpression isLikedSubQuery(QPost post, User user) {
        return jpaQueryFactory
                .selectFrom(post)
                .where(
                        postLike.post.eq(post),
                        postLike.user.eq(user)
                )
                .exists();
    }

    private BooleanExpression isNotPrivateOrAuthor(QPost post, User user) {
        return post.isPrivate.isFalse().or(post.user.eq(user));
    }

    private OrderSpecifier<?> getOrderSpecifier(PostOrderType orderType) {
        return switch (orderType) {
            case LIKE_COUNT -> postLike.count().desc();
            default -> post.id.desc(); // Default to ordering by created date
        };
    }
}
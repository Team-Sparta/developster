package com.example.developster.domain.post.main.repository;

import com.example.developster.domain.post.comment.main.entity.QComment;
import com.example.developster.domain.post.like.entity.QPostLike;
import com.example.developster.domain.post.main.dto.PostDetailInfo;
import com.example.developster.domain.post.main.dto.response.PostResponse;
import com.example.developster.domain.post.main.entity.QPost;
import com.example.developster.domain.post.main.enums.PostOrderType;
import com.example.developster.domain.post.media.dto.MediaInfo;
import com.example.developster.domain.post.media.repository.MediaQueryRepository;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final MediaQueryRepository mediaQueryRepository;

    QPost post = QPost.post;
    QPostLike postLike = QPostLike.postLike;
    QComment comment = QComment.comment;

    public PostDetailInfo getPostDetailById(Long postId, User user) {
        return Optional.ofNullable(
                        jpaQueryFactory
                                .select(Projections.constructor(
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
                                ))
                                .from(post)
                                .leftJoin(comment).on(comment.post.eq(post).and(comment.deletedAt.isNull()))
                                .leftJoin(postLike).on(postLike.post.eq(post))
                                .where(
                                        post.id.eq(postId),
                                        isNotPrivateOrAuthor(post, user),
                                        post.deletedAt.isNull()
                                )
                                .groupBy(post.id)
                                .fetchOne()
                )
                .orElseThrow(() -> new InvalidParamException(ErrorCode.NOT_FOUND_POST));
    }

    public Slice<PostResponse> getAllPosts(
            User user,
            Long lastPostId,
            int pageSize,
            PostOrderType orderType,
            LocalDate startDate,
            LocalDate endDate) {

        List<PostDetailInfo> postDetailList = jpaQueryFactory
                .select(Projections.constructor(
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
                ))
                .from(post)
                .leftJoin(comment).on(comment.post.eq(post).and(comment.deletedAt.isNull()))
                .leftJoin(postLike).on(postLike.post.eq(post))
                .where(
                        isNotPrivateOrAuthor(post, user),
                        post.deletedAt.isNull(),
                        noOffsetByPostId(post, lastPostId),
                        dateRangeFilter(post.createdAt, startDate, endDate)  // Simplified condition
                )
                .groupBy(post.id)
                .orderBy(getOrderSpecifier(orderType))
                .limit(pageSize + 1L)
                .fetch();

        // Direct mapping to PostResponse using stream without creating intermediate collections
        List<PostResponse> postResponseList = postDetailList.stream()
                .map(postDetail -> {
                    List<String> urlList = mediaQueryRepository.getUrlList(postDetail.postId());
                    MediaInfo mediaInfo = new MediaInfo(urlList, urlList.size());
                    return new PostResponse(postDetail, mediaInfo);
                })
                .collect(Collectors.toList());

        boolean hasNext = postResponseList.size() > pageSize;
        if (hasNext) {
            postResponseList.remove(pageSize);  // Remove last element if there is a next page
        }

        return new SliceImpl<>(postResponseList, PageRequest.ofSize(pageSize), hasNext);
    }

    // Simplified method for date range filter
    private BooleanExpression dateRangeFilter(DateTimePath<LocalDateTime> createdAt, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return createdAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        }
        return null;  // No filter when dates are not provided
    }

    // Simplified method for offset condition (null checks can be combined)
    private BooleanExpression noOffsetByPostId(QPost post, Long lastPostId) {
        return lastPostId == null ? null : post.id.lt(lastPostId);
    }

    // Sub-query for checking if post is liked by the user
    private BooleanExpression isLikedSubQuery(QPost post, User user) {
        return jpaQueryFactory
                .selectFrom(postLike)
                .where(
                        postLike.post.eq(post),
                        postLike.user.eq(user),
                        postLike.isLiked.isTrue()
                )
                .exists();
    }

    // Check if the post is not private or the user is the author
    private BooleanExpression isNotPrivateOrAuthor(QPost post, User user) {
        return post.isPrivate.isFalse().or(post.user.eq(user));
    }

    // Return OrderSpecifier based on sorting type
    private OrderSpecifier<?> getOrderSpecifier(PostOrderType orderType) {
        if (orderType == null) {
            return post.createdAt.desc();
        }
        return switch (orderType) {
            case LIKE_COUNT -> postLike.count().desc();
            case UPDATED_DATE -> post.updatedAt.desc();
        };
    }
}
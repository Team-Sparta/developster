package com.example.developster.domain.post.main.repository;

import com.example.developster.domain.post.comment.main.entity.QComment;
import com.example.developster.domain.post.like.entity.QPostLike;
import com.example.developster.domain.post.main.dto.PostDetailInfo;
import com.example.developster.domain.post.main.dto.response.PostResponse;
import com.example.developster.domain.post.main.entity.QPost;
import com.example.developster.domain.post.main.enums.PostOrderType;
import com.example.developster.domain.post.media.dto.MediaInfo;
import com.example.developster.domain.post.media.entity.QMedia;
import com.example.developster.domain.post.media.repository.MediaQueryRepository;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.querydsl.core.types.ConstructorExpression;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final MediaQueryRepository mediaQueryRepository;

    QPost post = QPost.post;
    QPostLike postLike = QPostLike.postLike;
    QComment comment = QComment.comment;
    QMedia media = QMedia.media;


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
                                isLikedSubQuery(post, user),
                                post.isPrivate
                        )
                )
                .from(post)
                .leftJoin(comment).on(comment.post.eq(post).and(comment.deletedAt.isNull()))
                .leftJoin(postLike).on(postLike.post.eq(post))
                .where(
                        post.id.eq(postId),
                        isNotPrivateOrAuthor(post, user),
                        post.deletedAt.isNull()
                )
                .groupBy(post.id)
                .fetchOne();

        return Optional.ofNullable(postDetailInfo)
                .orElseThrow(() -> new InvalidParamException(ErrorCode.NOT_FOUND_POST));
    }

    public Slice<PostResponse> getAllPosts(User user, Long lastPostId, int pageSize, PostOrderType orderType, LocalDate startDate, LocalDate endDate) {
        List<PostDetailInfo> postDetailList = jpaQueryFactory
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
                .leftJoin(comment).on(comment.post.eq(post).and(comment.deletedAt.isNull()))
                .leftJoin(postLike).on(postLike.post.eq(post))
                .where(
                        isNotPrivateOrAuthor(post, user),
                        post.deletedAt.isNull(),
                        noOffsetByPostId(post, lastPostId)
                )
                .groupBy(post.id)
                .orderBy(getOrderSpecifier(orderType))
                .limit(pageSize + 1L)
                .fetch();

        List<PostResponse> postResponseList = new java.util.ArrayList<>(postDetailList.stream()
                .map(postDetail -> {
                    List<String> urlList = mediaQueryRepository.getUrlList(postDetail.postId());
                    MediaInfo mediaInfo = new MediaInfo(urlList, urlList.size());
                    return new PostResponse(postDetail, mediaInfo);
                }).toList());

        boolean hasNext = postResponseList.size() > pageSize;
        if (hasNext) {
            postResponseList.remove(pageSize);
        }
        return new SliceImpl<>(postResponseList, PageRequest.ofSize(pageSize), hasNext);
    }

    private BooleanExpression noOffsetByPostId(QPost post, Long lastPostId) {
        return (lastPostId == null) ? null : post.id.lt(lastPostId);
    }

    private BooleanExpression isLikedSubQuery(QPost post, User user) {
        return jpaQueryFactory
                .selectFrom(postLike)
                .where(
                        postLike.post.eq(post),
                        postLike.user.eq(user),
                        postLike.isLike.isTrue()
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
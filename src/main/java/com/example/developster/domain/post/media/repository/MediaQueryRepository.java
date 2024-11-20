package com.example.developster.domain.post.media.repository;

import com.example.developster.domain.post.media.entity.QMedia;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MediaQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QMedia media = QMedia.media;

    public List<String> getImageUrlList(Long postId) {
        return jpaQueryFactory
                .select(media.url)
                .from(media)
                .where(
                        media.id.eq(postId),
                        media.deletedAt.isNull()
                )
                .fetch();

    }

}

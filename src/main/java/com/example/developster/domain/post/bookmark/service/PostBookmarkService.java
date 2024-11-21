package com.example.developster.domain.post.bookmark.service;

import com.example.developster.domain.post.bookmark.entity.PostBookmark;
import com.example.developster.domain.post.bookmark.repository.PostBookmarkJpaRepository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostBookmarkService {
    private final PostBookmarkJpaRepository postBookmarkJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public void savePost(User user, Long postId) {
        Post post = postJpaRepository.findByIdOrElseThrow(postId);
        Optional<PostBookmark> postBookmark = postBookmarkJpaRepository.findByUserAndPost(user, post);

        if (postBookmark.isEmpty()) {
            throw new BaseException(ErrorCode.ALREADY_BOOKMARKED_POST);
        }
        PostBookmark bookmark = PostBookmark.create(user, post);
        postBookmarkJpaRepository.save(bookmark);
    }

    public void unSavePost(User user, Long postId) {
        Post post = postJpaRepository.findByIdOrElseThrow(postId);
        Optional<PostBookmark> postBookmark = postBookmarkJpaRepository.findByUserAndPost(user, post);

        if (postBookmark.isEmpty()) {
            throw new BaseException(ErrorCode.NOT_FOUND_POST_BOOKMARK);
        }
        postBookmarkJpaRepository.delete(postBookmark.get());
    }
}

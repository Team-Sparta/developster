package com.example.developster.domain.post.main.service;

import com.example.developster.domain.post.main.dto.PostDetailInfo;
import com.example.developster.domain.post.main.dto.request.WritePostRequestDto;
import com.example.developster.domain.post.main.dto.response.PostIdResponseDto;
import com.example.developster.domain.post.main.dto.response.PostListResponseDto;
import com.example.developster.domain.post.main.dto.response.PostResponseDto;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.enums.PostOrderType;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.post.main.repository.PostQueryRepository;
import com.example.developster.domain.post.media.dto.MediaInfo;
import com.example.developster.domain.post.media.entity.Media;
import com.example.developster.domain.post.media.repository.MediaJpaRepository;
import com.example.developster.domain.post.media.repository.MediaQueryRepository;
import com.example.developster.domain.user.main.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PostService {
    private final PostJpaRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final MediaJpaRepository mediaRepository;
    private final MediaQueryRepository mediaQueryRepository;

    public PostIdResponseDto createPost(User user, WritePostRequestDto request) {
        Post newPost = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .isPrivate(request.getIsPrivate())
                .build();

        Post save = postRepository.save(newPost);

        storeMedia(request.getImageUrls(), request.getVideoUrl(), save);

        return new PostIdResponseDto(save.getId());
    }

    public PostListResponseDto loadPostList(User user, Long lastPostId, Integer pageSize, PostOrderType orderType, LocalDate startDate, LocalDate endDate) {
        Slice<PostResponseDto> allPosts = postQueryRepository.getAllPosts(user, lastPostId, pageSize, orderType, startDate, endDate);

        return new PostListResponseDto(allPosts);
    }

    @Transactional(readOnly = true)
    @Cacheable("posts")
    public PostResponseDto loadPost(User user, Long postId) {
        PostDetailInfo postDetailInfo = postQueryRepository.getPostDetailById(postId, user);
        List<String> urlList = mediaQueryRepository.getUrlList(postId);

        return new PostResponseDto(postDetailInfo, new MediaInfo(urlList, urlList.size()));
    }

    @Transactional
    @CachePut(value = "posts", key = "#postId")
    public PostIdResponseDto updatePost(Long userId, WritePostRequestDto request, Long postId) {
        Post post = postRepository.fetchPost(postId);

        post.validatePostWriter(userId);

        final List<Media> mediaList = mediaQueryRepository.getMediaList(post.getId());
        mediaList.forEach(Media::delete);

        storeMedia(request.getImageUrls(), request.getVideoUrl(), post);
        post.update(request);

        return new PostIdResponseDto(post.getId());
    }

    @Transactional
    @CacheEvict(value = "posts", key = "#postId")
    public PostIdResponseDto deletePost(Long userId, Long postId) {
        Post post = postRepository.fetchPost(postId);

        post.validatePostWriter(userId);
        post.delete();

        return new PostIdResponseDto(postId);
    }

    private void storeMedia(
            final List<String> imageUrls,
            final String videoUrl,
            final Post post
    ) {
        for (String imageUrl : imageUrls) {
            Media media = Media.create(post, imageUrl);
            mediaRepository.save(media);
        }
        mediaRepository.save(Media.create(post, videoUrl));
    }
}

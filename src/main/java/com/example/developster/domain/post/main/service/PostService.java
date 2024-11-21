package com.example.developster.domain.post.main.service;

import com.example.developster.domain.post.main.dto.PostDetailInfo;
import com.example.developster.domain.post.main.dto.request.WritePostRequest;
import com.example.developster.domain.post.main.dto.response.PostIdResponse;
import com.example.developster.domain.post.main.dto.response.PostListResponse;
import com.example.developster.domain.post.main.dto.response.PostResponse;
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
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class PostService {
    private final PostJpaRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final MediaJpaRepository mediaRepository;
    private final MediaQueryRepository mediaQueryRepository;

    public PostIdResponse createPost(User user, WritePostRequest request) {
        Post newPost = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .isPrivate(request.getIsPrivate())
                .build();

        Post save = postRepository.save(newPost);

        storeImageList(request.getImageUrls(), save);

        return new PostIdResponse(save.getId());
    }

    public PostListResponse loadPostList(User user, Long lastPostId, Integer pageSize, PostOrderType orderType) {
        Slice<PostDetailInfo> allPosts = postQueryRepository.getAllPosts(user, lastPostId, pageSize, orderType);

        return new PostListResponse(allPosts);
    }

    @Transactional
    public PostResponse loadPost(User user, Long postId) {
        PostDetailInfo postDetailInfo = postQueryRepository.getPostDetailById(postId, user);
        final List<String> urlList = mediaQueryRepository.getUrlList(postId);

        return new PostResponse(postDetailInfo, new MediaInfo(urlList, urlList.size()));
    }

    @Transactional
    public PostIdResponse updatePost(Long userId, WritePostRequest request, Long postId) {
        Post post = postRepository.fetchPost(postId);

        post.validatePostWriter(userId);
        post.update(request);

        return new PostIdResponse(post.getId());
    }

    @Transactional
    public PostIdResponse deletePost(Long userId, Long postId) {
        Post post = postRepository.fetchPost(postId);

        post.validatePostWriter(userId);
        post.delete();

        return new PostIdResponse(postId);
    }

    private void storeImageList(
            final List<String> imageUrls,
            final Post post
    ) {
        for (String imageUrl : imageUrls) {
            Media media = Media.create(post, imageUrl);
            mediaRepository.save(media);
        }
    }
}

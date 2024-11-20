package com.example.developster.domain.post.main.service;

import com.example.developster.domain.post.main.dto.PostSummary;
import com.example.developster.domain.post.main.dto.request.WritePostRequest;
import com.example.developster.domain.post.main.dto.response.PostIdResponse;
import com.example.developster.domain.post.main.dto.response.PostListResponse;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.enums.PostOrderType;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.post.media.entity.Media;
import com.example.developster.domain.post.media.repository.MediaJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostJpaRepository postRepository;
    private final MediaJpaRepository mediaRepository;

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

    public PostListResponse loadPostList(Integer lastPostId, Integer size, PostOrderType orderType) {
        return null;
    }

    public PostSummary loadPost(Long postId) {
        Post post = postRepository.findByIdOrElseThrow(postId);

        return PostSummary.of(post);
    }

    public PostIdResponse updatePost(Long userId, WritePostRequest request, Long postId) {
        Post post = postRepository.findByIdOrElseThrow(postId);

        post.validateScheduleWriter(userId);
        post.update(request);

        return new PostIdResponse(post.getId());
    }

    public PostIdResponse deletePost(Long userId, Long postId) {
        Post post = postRepository.findByIdOrElseThrow(postId);

        post.validateScheduleWriter(userId);
        post.delete();

        return new PostIdResponse(postId);
    }

    public PostListResponse loadMyPostList(Long userId, Integer lastPostId, Integer size, PostOrderType orderType) {
        return null;
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

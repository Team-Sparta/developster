package com.example.developster.domain.post.main.dto.response;

import com.example.developster.domain.post.main.dto.PostDetailInfo;
import com.example.developster.domain.post.main.dto.PostSummary;
import org.springframework.data.domain.Slice;

public record PostListResponse(
        Slice<PostResponse> posts
) {
}

package com.example.developster.domain.post.media.dto;

import java.util.List;

public record MediaInfo(
        List<String> mediaUrls,
        int mediaCount
) {
}

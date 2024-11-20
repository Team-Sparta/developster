package com.example.developster.domain.post.main.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class WritePostRequest {
    @NotEmpty(message = "제목이 누락되었습니다.")
    @Size(min = 5, max = 100, message = "재목은 최소 5글자, 최대 100글자까지 작성 가능합니다.")
    private String title;

    @Schema(description = "스케줄 내용")
    @NotEmpty(message = "내용이 누락되었습니다.")
    @Size(max = 1000, message = "내용은 최대 1000글자까지 가능합니다.")
    private String content;

    @Schema(description = "스케줄 이미지 urls")
    private List<String> imageUrls;

    @Schema(description = "스케줄 비디오 url")
    private String videoUrl;

    @Schema(description = "스케줄 내용")
    @NotNull(message = "isPrivate이 누락되었습니다.")
    private Boolean isPrivate;
}

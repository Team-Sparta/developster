CREATE TABLE  posts
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '게시글 고유 번호',
    user_id    BIGINT UNSIGNED NOT NULL COMMENT '회원 고유 번호',
    title      VARCHAR(100)  NOT NULL COMMENT '제목',
    content    VARCHAR(1200) NOT NULL COMMENT '내용',
    is_private BOOLEAN       NOT NULL COMMENT '게시글 숨김 여부',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    deleted_at DATETIME DEFAULT NULL COMMENT '삭제 일시',
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE post_likes
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '게시물 좋아요 고유 번호',
    post_id    BIGINT UNSIGNED NOT NULL COMMENT '게시물 고유 번호',
    user_id    BIGINT UNSIGNED NOT NULL COMMENT '회원 고유 번호',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    CONSTRAINT fk_post_likes_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_post_likes_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE KEY uk_post_user (post_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE post_bookmarks
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '게시물 북마크 고유 번호',
    post_id    BIGINT UNSIGNED NOT NULL COMMENT '게시물 고유 번호',
    user_id    BIGINT UNSIGNED NOT NULL COMMENT '회원 고유 번호',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    CONSTRAINT fk_post_bookmarks_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_post_bookmarks_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE KEY uk_post_user (post_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE medias
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '미디어 고유 번호',
    post_id    BIGINT UNSIGNED NOT NULL COMMENT '게시물 고유 번호',
    url        VARCHAR(1200) NOT NULL COMMENT '미디어 URL',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    deleted_at DATETIME DEFAULT NULL COMMENT '삭제 일시',
    CONSTRAINT fk_media_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
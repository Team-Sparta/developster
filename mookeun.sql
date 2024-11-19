CREATE TABLE comments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          post_id BIGINT NOT NULL,
                          parent_id BIGINT,
                          contents VARCHAR(200) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                          FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE comment_likes (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               post_id BIGINT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE,
                               FOREIGN KEY (post_id) REFERENCES POSTS(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE COMMENT_LIKES
    ADD CONSTRAINT UNIQUE_USER_POST
        UNIQUE (user_id, post_id);
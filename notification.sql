CREATE TABLE notifications (
    id BIGINT NOT NULL PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    is_read BOOLEAN NOT NULL,
    user_id BIGINT,
    type ENUM('COMMENT', 'LIKE', 'FOLLOW', 'MENTION') NOT NULL
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
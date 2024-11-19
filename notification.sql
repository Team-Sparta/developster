CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    is_read BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    alert_type ENUM('COMMENT', 'LIKE', 'FOLLOW', 'MENTION') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
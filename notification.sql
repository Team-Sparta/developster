CREATE TABLE notifications (
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    is_read BOOLEAN NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    alert_type ENUM('COMMENT', 'LIKE', 'FOLLOW', 'MENTION') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    related_url VARCHAR(250) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
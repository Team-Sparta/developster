CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    email VARCHAR(20) NOT NULL UNIQUE ,
    name VARCHAR(10) NOT NULL ,
    password VARCHAR(20) NOT NULL ,
    bio VARCHAR(50),
    role ENUM('role_admin', 'role_user') DEFAULT 'role_user',
    status ENUM('blocked', 'active', 'inactive', 'withdrawal') DEFAULT 'active',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

CREATE TABLE follows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    followed_user_id BIGINT NOT NULL ,
    followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (followed_user_id) REFERENCES users (id),
    UNIQUE KEY follow (user_id,followed_user_id)
);
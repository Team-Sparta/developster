CREATE TABLE users (
    user_id BIGINT NOT NULL PRIMARY KEY ,
    email VARCHAR(20) NOT NULL UNIQUE ,
    name VARCHAR(10) NOT NULL ,
    password VARCHAR(20) NOT NULL ,
    bio VARCHAR(50)
);


CREATE TABLE follows (
    id BIGINT NOT NULL PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    followed_user_id BIGINT NOT NULL ,
    followed_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) references users (user_id),
    FOREIGN KEY (followed_user_id) references users (user_id),
    UNIQUE KEY follow (user_id,followed_user_id)
);
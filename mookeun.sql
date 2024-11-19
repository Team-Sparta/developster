CREATE TABLE comments(
    id bigint auto_increment primary key,
    post_id bigint not null,
    parent_id bigint,
    contents varchar(200),
    created_at timestamp default current_timestamp,
    modified_at timestamp default current_timestamp on update current_timestamp,
    foreign key (post_id) references posts(id) on delete  cascade,
    foreign key (parent_id) references comments(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table comment_likes(
    id bigint auto_increment primary key,
    user_id bigint not null,
    post_id bigint not null,
    created_at timestamp default current_timestamp,
    foreign key (user_id) references users(user_id) on delete cascade,
    foreign key (post_id) references posts(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE comment_likes
    ADD CONSTRAINT unique_user_post
        UNIQUE (user_id, post_id);

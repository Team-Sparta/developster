CREATE TABLE comments(
    id int auto_increment primary key,
    post_id int not null,
    parent_id int not null,
    contents varchar(200),
    created_at timestamp default current_timestamp,
    modified_at timestamp default current_timestamp on update current_timestamp,
    foreign key (post_id) references users(user_id) on delete  cascade,
    foreign key (parent_id) references comments(id) on delete cascade
);

create table comment_likes(
    id int auto_increment primary key,
    user_id int not null,
    post_id int not null,
    created_at timestamp default current_timestamp,
    foreign key (user_id) references users(user_id) on delete cascade,
    foreign key (post_id) references posts(id) on delete cascade
);

ALTER TABLE comment_likes
    ADD CONSTRAINT unique_user_post
        UNIQUE (user_id, post_id);

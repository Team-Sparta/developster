CREATE TABLE comment(
    id int auto_increment primary key,
    post_id int not null,
    parent_id int not null,
    contents varchar(200),
    created_at timestamp default current_timestamp,
    modified_at timestamp default current_timestamp on update current_timestamp,
    foreign key (post_id) references user(id) on delete  cascade,
    foreign key (parent_id) references comment(id) on delete cascade
);
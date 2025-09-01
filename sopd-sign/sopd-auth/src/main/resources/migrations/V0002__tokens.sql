create table white_list_access_tokens(
    token varchar(512),
    user_id int references users(id)
);

create index on white_list_access_tokens using hash(token)
create table white_list_access_tokens(
    token varchar(2048) primary key,
    user_id int references users(id)
);
create index on white_list_access_tokens using btree(user_id);

create table white_list_refresh_tokens(
    token varchar(2048) primary key,
    user_id int references users(id)
);
create index on white_list_refresh_tokens using btree(user_id);

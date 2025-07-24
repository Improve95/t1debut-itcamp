create table white_list_access_tokens(
    token varchar(1024) primary key,
    user_id int references users(id)
);
create index on white_list_access_tokens using btree(user_id);


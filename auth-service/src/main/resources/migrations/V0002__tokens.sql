create table white_list_access_tokens(
    token char(256) primary key,
    user_id int references users(id)
);
create unique index on white_list_access_tokens using btree(user_id);

create table white_list_refresh_tokens(
    token char(256) primary key,
    user_id int references users(id)
);
create unique index on white_list_refresh_tokens using btree(user_id);
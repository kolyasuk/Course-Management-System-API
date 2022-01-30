alter table course
    add status varchar(25) not null,
    add description varchar(1000) not null,
    add created_at timestamp not null default current_timestamp,
    add modified_at timestamp;

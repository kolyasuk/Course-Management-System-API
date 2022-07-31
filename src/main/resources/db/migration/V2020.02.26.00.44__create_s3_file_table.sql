create table s3_file (
    id bigint auto_increment,
    file_key varchar(255) not null unique,
    user_id bigint not null,
    created_at datetime default current_timestamp not null,
    FOREIGN KEY (user_id) REFERENCES user(Id),
    constraint s3_file_pk primary key (id)
);


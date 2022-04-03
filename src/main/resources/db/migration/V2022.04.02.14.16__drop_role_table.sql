alter table user drop foreign key user_ibfk_1;

alter table user drop column role_id;

alter table user
    add role varchar(20) not null;

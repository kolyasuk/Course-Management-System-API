alter table course add status varchar(255) not null;
alter table course add description varchar(1000) not null;
alter table course add created_at timestamp not null default current_timestamp;
alter table course add modified_at timestamp;

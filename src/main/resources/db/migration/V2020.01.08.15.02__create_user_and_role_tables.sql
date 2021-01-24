CREATE TABLE USER (id int AUTO_INCREMENT PRIMARY KEY,
                   username varchar(25) not null unique,
                   password varchar(500) not null,
                   full_name varchar(500) not null,
                   email varchar(40) not null,
                   created_at timestamp not null default current_timestamp,
                   modified_at timestamp);

CREATE TABLE ROLE (id int AUTO_INCREMENT PRIMARY KEY,
                   name varchar(25) not null unique);

INSERT INTO ROLE (name)
VALUES ('ROLE_STUDENT'),
       ('ROLE_INSTRUCTOR'),
       ('ROLE_ADMIN');

CREATE TABLE USER_ROLES (user_id int not null references USER(id),
                        role_id int not null references ROLE(id),
                        foreign key (user_id) references USER(id),
                        foreign key (role_id) references ROLE(id));
CREATE TABLE user (id int AUTO_INCREMENT PRIMARY KEY,
                   username varchar(25) not null unique,
                   password varchar(500) not null,
                   full_name varchar(500) not null,
                   email varchar(40) not null,
                   created_at timestamp not null default current_timestamp,
                   modified_at timestamp);

CREATE TABLE role (id int AUTO_INCREMENT PRIMARY KEY,
                   name varchar(25) not null unique);

INSERT INTO role (name)
VALUES ('ROLE_STUDENT'),
       ('ROLE_INSTRUCTOR'),
       ('ROLE_ADMIN');

CREATE TABLE user_roles (user_id int not null references user(id),
                        role_id int not null references role(id),
                        foreign key (user_id) references user(id),
                        foreign key (role_id) references role(id));
CREATE TABLE role (id bigint AUTO_INCREMENT PRIMARY KEY,
                   role varchar(25) not null unique);

INSERT INTO role (role)
VALUES ('ROLE_STUDENT'),
       ('ROLE_INSTRUCTOR'),
       ('ROLE_ADMIN');

CREATE TABLE user (id bigint AUTO_INCREMENT PRIMARY KEY,
                   username varchar(25) not null unique,
                   password varchar(500) not null,
                   full_name varchar(500) not null,
                   email varchar(40) not null,
                   role_id bigint not null,
                   created_at timestamp not null default current_timestamp,
                   modified_at timestamp,
                   FOREIGN KEY (role_id) REFERENCES role(Id)
                   );
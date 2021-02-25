CREATE TABLE instructor (
    id bigint auto_increment primary key,
    first_name varchar(40) not null,
    last_name varchar(40) not null,
    email varchar(40) not null,
    info varchar(500) not null,
    user_id int(11) not null references user(id)
);

CREATE TABLE course (
    id bigint auto_increment primary key,
    name varchar(120) not null
);

CREATE TABLE instructor_course (
    instructor_id bigint not null references instructor(id),
    course_id bigint not null references course(id),
    primary key (instructor_id, course_id)
);

CREATE TABLE student (
    id bigint auto_increment primary key,
    first_name varchar(40) not null,
    last_name varchar(40) not null,
    email varchar(40) not null,
    user_id int(11) not null references user(id)
);

CREATE TABLE student_course (
    student_id bigint not null references student(id),
    course_id bigint not null references course(id),
    primary key (student_id, course_id)
);

CREATE TABLE lesson (
    id bigint auto_increment primary key,
    name varchar(120) not null,
    description text(50000) not null,
    course_id bigint not null references course(id)
);





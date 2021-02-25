CREATE TABLE homework (
    id bigint auto_increment primary key,
    name varchar(120) not null,
    description text(50000) not null,
    lesson_id bigint not null references lesson(id)
);

CREATE TABLE student_homework (
    student_id bigint not null references student(id),
    homework_id bigint not null references homework(id),
    primary key (student_id, homework_id)
);






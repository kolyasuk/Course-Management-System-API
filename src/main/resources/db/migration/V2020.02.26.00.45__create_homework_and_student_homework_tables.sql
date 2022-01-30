CREATE TABLE homework (
    id bigint auto_increment primary key,
    name varchar(120) not null,
    description text(50000) not null,
    lesson_id bigint not null,
    FOREIGN KEY (lesson_id) REFERENCES lesson(Id)
);

CREATE TABLE student_homework (
    student_id bigint not null,
    homework_id bigint not null,
    FOREIGN KEY (student_id) REFERENCES student(Id),
    FOREIGN KEY (homework_id) REFERENCES homework(Id),
    primary key (student_id, homework_id)
);






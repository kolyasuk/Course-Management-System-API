create table homework_file
(
    student_id bigint not null references student(id),
    lesson_id bigint not null references lesson(id),
    homework_file_id bigint not null references s3_file(id),
    FOREIGN KEY (student_id) REFERENCES student(Id),
    FOREIGN KEY (lesson_id) REFERENCES lesson(Id),
    FOREIGN KEY (homework_file_id) REFERENCES s3_file(Id),
    primary key (student_id, lesson_id, homework_file_id)
);
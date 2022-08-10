CREATE TABLE student_lesson (
    id bigint not null primary key auto_increment,
    student_id bigint not null,
    lesson_id bigint not null,
    FOREIGN KEY (lesson_id) REFERENCES lesson(Id),
    FOREIGN KEY (student_id) REFERENCES student(Id)
);






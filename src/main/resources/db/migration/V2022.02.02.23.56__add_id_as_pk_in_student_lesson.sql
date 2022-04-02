alter table student_lesson
    add id bigint not null;

alter table student_lesson drop foreign key student_lesson_ibfk_1;

alter table student_lesson drop primary key, add primary key (id);

alter table student_lesson modify id bigint auto_increment;

ALTER TABLE student_lesson
    ADD FOREIGN KEY (lesson_id)
        REFERENCES lesson(id);

ALTER TABLE student_lesson
    ADD FOREIGN KEY (student_id)
        REFERENCES student(id);
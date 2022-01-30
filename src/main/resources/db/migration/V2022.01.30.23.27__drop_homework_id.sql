alter table student_lesson drop column homework_id;

alter table student_lesson
    add lesson_id bigint not null;

ALTER TABLE student_lesson
    ADD FOREIGN KEY (lesson_id)
        REFERENCES lesson(id);
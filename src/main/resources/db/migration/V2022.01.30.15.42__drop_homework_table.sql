alter table student_homework drop foreign key student_homework_ibfk_2;

drop table homework;

alter table lesson
    add homework varchar(5000) null;

rename table student_homework to student_lesson;

alter table student_lesson
    add homework_file_id bigint null;

ALTER TABLE student_lesson
    ADD FOREIGN KEY (homework_file_id)
    REFERENCES s3_file(id);

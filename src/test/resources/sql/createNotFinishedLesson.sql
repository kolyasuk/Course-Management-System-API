INSERT INTO user (password, full_name, email, role, created_at, modified_at)
VALUES ('password', 'instructor', 'instructor@instructor.com', 'ROLE_INSTRUCTOR', DEFAULT, null);

INSERT INTO instructor (first_name, last_name, info, user_id)
VALUES ('Instructor 1st name', 'Instructor last name', 'Instructor info', 2);

INSERT INTO course (name, status, description, created_at, modified_at)
VALUES ('Course 1', 'INACTIVE', 'Description', DEFAULT, null);

INSERT INTO lesson (name, description, course_id, homework, homework_finish_date, lesson_date, instructor_id)
VALUES
       ('Lesson 1', 'Lesson description', 1, 'This is test homework text', '9999-01-01', '9999-01-01', 1),
       ('Lesson 2', 'Lesson description', 1, 'This is test homework text', '1999-01-01', '9999-01-01', 1);
alter table lesson
    add instructor_id bigint not null;

ALTER TABLE lesson
    ADD FOREIGN KEY (instructor_id)
        REFERENCES instructor(id);
package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.domain.payload.CourseData;

import javax.validation.Valid;

public interface CourseService {

    Course getById(Long courseId);

    Course getActiveById(Long courseId);

    CourseDTO create(@Valid CourseData courseData);

    CourseDTO update(Long courseId, @Valid CourseData courseData);

    void start(Long courseId);

    void finish(Long courseId);

    boolean existsNotFinishedLessons(Long courseId);

    CourseDTO assignInstructor(Long courseId, Long instructorId);

    CourseDTO assignStudent(Long courseId, Long studentId);

}

package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.domain.payload.CourseData;

public interface CourseService {

    Course getById(Long id);

    CourseDTO create(CourseData courseData);

    CourseDTO update(Long id, CourseData courseData);

    void start(Long id);

    CourseDTO assignInstructor(Long courseId, Long instructorId);

    CourseDTO assignStudent(Long courseId, Long studentId);

}

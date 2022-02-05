package edu.sombra.cms.service;

import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;

import javax.validation.Valid;

public interface StudentCourseService {

    StudentCourse getByStudentAndCourse(Long studentId, Long courseId);

    void feedback(@Valid StudentCourseFeedbackData studentCourseFeedbackData);

}

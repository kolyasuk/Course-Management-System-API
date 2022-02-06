package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;

import javax.validation.Valid;

public interface StudentCourseService {

    StudentCourse getByStudentAndCourse(Long studentId, Long courseId);

    StudentCourseDTO getDTOByCourseId(Long courseId);

    void feedback(Long courseId, @Valid StudentCourseFeedbackData studentCourseFeedbackData);

}

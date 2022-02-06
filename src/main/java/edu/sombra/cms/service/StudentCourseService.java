package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.messages.SomethingWentWrongException;

import javax.validation.Valid;

public interface StudentCourseService {

    StudentCourse getByStudentAndCourse(Long studentId, Long courseId) throws SomethingWentWrongException;

    StudentCourseDTO getDTOByCourseId(Long courseId) throws SomethingWentWrongException;

    void feedback(Long courseId, @Valid StudentCourseFeedbackData studentCourseFeedbackData) throws SomethingWentWrongException;

}

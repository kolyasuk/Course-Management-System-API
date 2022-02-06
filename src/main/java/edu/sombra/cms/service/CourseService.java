package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.dto.LessonOverviewDTO;
import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.messages.SomethingWentWrongException;

import javax.validation.Valid;
import java.util.List;

public interface CourseService {

    Course getById(Long courseId) throws SomethingWentWrongException;

    CourseDTO getDTOById(Long courseId) throws SomethingWentWrongException;

    Course getActiveById(Long courseId) throws SomethingWentWrongException;

    CourseDTO create(@Valid CourseData courseData) throws SomethingWentWrongException;

    CourseDTO update(Long courseId, @Valid CourseData courseData) throws SomethingWentWrongException;

    void start(Long courseId) throws SomethingWentWrongException;

    void finish(Long courseId) throws SomethingWentWrongException;

    List<LessonOverviewDTO> lessonList(Long courseId) throws SomethingWentWrongException;

    boolean existsNotFinishedLessons(Long courseId);

    CourseDTO assignInstructor(Long courseId, Long instructorId) throws SomethingWentWrongException;

    CourseDTO assignStudent(Long courseId, Long studentId) throws SomethingWentWrongException;

    List<StudentOverviewDTO> courseStudentList(Long courseId) throws SomethingWentWrongException;

}

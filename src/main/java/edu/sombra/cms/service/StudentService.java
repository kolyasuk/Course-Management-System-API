package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.OverviewPageDTO;
import edu.sombra.cms.domain.dto.StudentCourseOverviewDTO;
import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.payload.StudentData;
import edu.sombra.cms.messages.SomethingWentWrongException;

import javax.validation.Valid;
import java.util.List;

public interface StudentService {

    Student getByStudentId(Long id) throws SomethingWentWrongException;

    Student getByUserId(Long id) throws SomethingWentWrongException;

    Student getLoggedStudent() throws SomethingWentWrongException;

    List<Student> getByIdList(List<Long> ids) throws SomethingWentWrongException;

    StudentDTO create(@Valid StudentData studentData, Long userId) throws SomethingWentWrongException;

    List<StudentCourseOverviewDTO> courseList() throws SomethingWentWrongException;

    List<OverviewPageDTO.ComingLessonDTO> comingLesson() throws SomethingWentWrongException;

}

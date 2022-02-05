package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.payload.StudentData;

import javax.validation.Valid;
import java.util.List;

public interface StudentService {

    Student getById(Long id);

    List<Student> getByIdList(List<Long> ids);

    StudentDTO create(@Valid StudentData studentData, Long userId);

    List<CourseOverviewDTO> courseList();

}

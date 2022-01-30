package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.payload.StudentData;

import java.util.List;

public interface StudentService {

    Student getById(Long id);

    List<Student> getByIdList(List<Long> ids);

    StudentDTO create(StudentData studentData, Long userId);

}

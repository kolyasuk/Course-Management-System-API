package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper extends AbstractMapper<Student, StudentDTO> {

    public StudentDTO to(Student Student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(Student.getId());
        studentDTO.setFirstName(Student.getFirstName());
        studentDTO.setLastName(Student.getLastName());
        studentDTO.setEmail(Student.getEmail());

        return studentDTO;
    }
}

package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper extends AbstractMapper<Long, StudentDTO> {

    @Lazy
    private final StudentService studentService;

    public StudentDTO to(Long id) throws SomethingWentWrongException {
        Student student = studentService.getByStudentId(id);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());

        return studentDTO;
    }
}

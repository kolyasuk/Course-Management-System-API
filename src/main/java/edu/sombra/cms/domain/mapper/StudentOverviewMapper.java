package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentOverviewMapper extends AbstractMapper<Long, StudentOverviewDTO> {

    @Lazy
    private final StudentService studentService;

    public StudentOverviewDTO to(Long id) throws SomethingWentWrongException {
        Student student = studentService.getByStudentId(id);

        StudentOverviewDTO studentOverviewDTO = new StudentOverviewDTO();
        studentOverviewDTO.setId(student.getId());
        studentOverviewDTO.setFirstName(student.getFirstName());
        studentOverviewDTO.setLastName(student.getLastName());

        return studentOverviewDTO;
    }
}

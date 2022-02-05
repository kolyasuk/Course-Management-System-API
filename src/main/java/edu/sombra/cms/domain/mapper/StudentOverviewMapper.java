package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentOverviewMapper extends AbstractMapper<Student, StudentOverviewDTO> {

    public StudentOverviewDTO to(Student Student){
        StudentOverviewDTO studentOverviewDTO = new StudentOverviewDTO();
        studentOverviewDTO.setId(Student.getId());
        studentOverviewDTO.setFirstName(Student.getFirstName());
        studentOverviewDTO.setLastName(Student.getLastName());

        return studentOverviewDTO;
    }
}

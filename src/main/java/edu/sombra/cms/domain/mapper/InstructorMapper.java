package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.entity.Instructor;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper extends AbstractMapper<Instructor, InstructorDTO> {

    public InstructorDTO to(Instructor instructor){
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setFirstName(instructor.getFirstName());
        instructorDTO.setLastName(instructor.getLastName());
        instructorDTO.setEmail(instructor.getEmail());
        instructorDTO.setInfo(instructor.getInfo());

        return instructorDTO;
    }
}

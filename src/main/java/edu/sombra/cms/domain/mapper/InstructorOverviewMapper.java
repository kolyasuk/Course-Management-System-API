package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.InstructorOverviewDTO;
import edu.sombra.cms.domain.entity.Instructor;
import org.springframework.stereotype.Component;

@Component
public class InstructorOverviewMapper extends AbstractMapper<Instructor, InstructorOverviewDTO> {

    @Override
    public InstructorOverviewDTO to(Instructor instructor){
        InstructorOverviewDTO instructorOverviewDTO = new InstructorOverviewDTO();
        instructorOverviewDTO.setId(instructor.getId());
        instructorOverviewDTO.setFirstName(instructor.getFirstName());
        instructorOverviewDTO.setLastName(instructor.getLastName());

        return instructorOverviewDTO;
    }
}

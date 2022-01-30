package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.OverviewInstructorDTO;
import edu.sombra.cms.domain.entity.Instructor;
import org.springframework.stereotype.Component;

@Component
public class OverviewInstructorMapper extends AbstractMapper<Instructor, OverviewInstructorDTO> {

    public OverviewInstructorDTO to(Instructor instructor){
        OverviewInstructorDTO overviewInstructorDTO = new OverviewInstructorDTO();
        overviewInstructorDTO.setId(instructor.getId());
        overviewInstructorDTO.setFirstName(instructor.getFirstName());
        overviewInstructorDTO.setLastName(instructor.getLastName());

        return overviewInstructorDTO;
    }
}

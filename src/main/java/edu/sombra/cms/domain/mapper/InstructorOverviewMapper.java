package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.InstructorOverviewDTO;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstructorOverviewMapper extends AbstractMapper<Long, InstructorOverviewDTO> {

    @Lazy
    private final InstructorService instructorService;

    @Override
    public InstructorOverviewDTO to(Long id) throws SomethingWentWrongException {
        Instructor instructor = instructorService.getById(id);

        InstructorOverviewDTO instructorOverviewDTO = new InstructorOverviewDTO();
        instructorOverviewDTO.setId(instructor.getId());
        instructorOverviewDTO.setFirstName(instructor.getFirstName());
        instructorOverviewDTO.setLastName(instructor.getLastName());

        return instructorOverviewDTO;
    }
}

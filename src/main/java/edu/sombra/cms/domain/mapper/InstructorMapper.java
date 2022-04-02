package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstructorMapper extends AbstractMapper<Long, InstructorDTO> {

    @Lazy
    private final InstructorService instructorService;

    public InstructorDTO to(Long id) throws SomethingWentWrongException {
        Instructor instructor = instructorService.getById(id);

        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setFirstName(instructor.getFirstName());
        instructorDTO.setLastName(instructor.getLastName());
        instructorDTO.setEmail(instructor.getEmail());
        instructorDTO.setInfo(instructor.getInfo());

        return instructorDTO;
    }
}

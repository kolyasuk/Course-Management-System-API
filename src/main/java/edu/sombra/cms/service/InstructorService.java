package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.payload.InstructorData;

import java.util.List;

public interface InstructorService {

    Instructor getById(Long id);

    List<Instructor> getByIdList(List<Long> ids);

    InstructorDTO create(InstructorData instructorData, Long userId);

}

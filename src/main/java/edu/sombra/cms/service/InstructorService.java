package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.payload.InstructorData;

import javax.validation.Valid;
import java.util.List;

public interface InstructorService {

    Instructor getById(Long id);

    List<Instructor> getByIdList(List<Long> ids);

    InstructorDTO create(@Valid InstructorData instructorData, Long userId);

    List<CourseOverviewDTO> courseList();

}

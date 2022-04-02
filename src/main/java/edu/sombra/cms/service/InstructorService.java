package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.payload.InstructorData;
import edu.sombra.cms.messages.SomethingWentWrongException;

import javax.validation.Valid;
import java.util.List;

public interface InstructorService {

    Instructor getByStudentId(Long id) throws SomethingWentWrongException;

    Instructor getByUserId(Long id) throws SomethingWentWrongException;

    Instructor getById(Long id) throws SomethingWentWrongException;

    List<Instructor> getByIdList(List<Long> ids) throws SomethingWentWrongException;

    Instructor getLoggedInstructor() throws SomethingWentWrongException;

    InstructorDTO create(@Valid InstructorData instructorData, Long userId) throws SomethingWentWrongException;

    List<CourseOverviewDTO> courseList() throws SomethingWentWrongException;

}

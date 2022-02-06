package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.mapper.CourseOverviewMapper;
import edu.sombra.cms.domain.mapper.InstructorMapper;
import edu.sombra.cms.domain.payload.InstructorData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.InstructorRepository;
import edu.sombra.cms.service.InstructorService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static edu.sombra.cms.messages.InstructorMessage.*;

@Service
@Validated
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final UserService userService;
    private final CourseOverviewMapper courseOverviewMapper;

    @Override
    public Instructor getById(Long id) throws SomethingWentWrongException {
        return instructorRepository.findById(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public List<Instructor> getByIdList(List<Long> ids) throws SomethingWentWrongException {
        return Optional.of(instructorRepository.findAllById(ids)).filter(o -> !o.isEmpty())
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public InstructorDTO create(@Valid InstructorData instructorData, Long userId) throws SomethingWentWrongException {
        var user = getInstructorUser(userId);

        if(user.getInstructor() != null){
            throw INFO_ALREADY_CREATED.ofException();
        }

        Instructor instructor = new Instructor();

        instructor.setFirstName(instructorData.getFirstName());
        instructor.setLastName(instructorData.getLastName());
        instructor.setEmail(instructorData.getEmail());
        instructor.setInfo(instructorData.getInfo());
        instructor.setUser(user);

        return instructorMapper.to(instructorRepository.save(instructor));
    }

    @Override
    public List<CourseOverviewDTO> courseList() throws SomethingWentWrongException {
        var loggedUser = userService.getLoggedUser();
        if(!loggedUser.isInstructor())
            throw USER_IS_NOT_INSTRUCTOR.ofException();

        return courseOverviewMapper.toList(loggedUser.getInstructor().getCourses());
    }

    private User getInstructorUser(Long userId) throws SomethingWentWrongException {
        if(userService.getLoggedUser().isAdmin()){
            if(userId == null)
                throw EMPTY_USER_ID.ofException();

            return userService.findUserById(userId);
        }

        return userService.getLoggedUser();
    }
}

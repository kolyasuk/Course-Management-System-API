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
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static edu.sombra.cms.messages.InstructorMessage.NOT_FOUND;
import static edu.sombra.cms.messages.InstructorMessage.USER_IS_NOT_INSTRUCTOR;
import static edu.sombra.cms.messages.StudentMessage.INFO_ALREADY_CREATED;

@Service
@Validated
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final UserService userService;
    private final CourseOverviewMapper courseOverviewMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorServiceImpl.class);

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Instructor getByStudentId(Long id) throws SomethingWentWrongException {
        return instructorRepository.findById(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Instructor getByUserId(Long id) throws SomethingWentWrongException {
        return instructorRepository.findByUserId(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Instructor getById(Long id) throws SomethingWentWrongException {
        return instructorRepository.findById(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public List<Instructor> getByIdList(List<Long> ids) throws SomethingWentWrongException {
        return Optional.of(instructorRepository.findAllById(ids)).filter(o -> !o.isEmpty())
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Instructor getLoggedInstructor() throws SomethingWentWrongException {
        return getByUserId(SecurityUtil.getLoggedUserId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public InstructorDTO create(@Valid InstructorData instructorData, Long userId) throws SomethingWentWrongException {
        userId = Optional.ofNullable(userId).orElse(SecurityUtil.getLoggedUserId());
        if(instructorRepository.findByUserId(userId).isPresent()){
            throw INFO_ALREADY_CREATED.ofException();
        }

        Instructor instructor = new Instructor();

        instructor.setFirstName(instructorData.getFirstName());
        instructor.setLastName(instructorData.getLastName());
        instructor.setEmail(instructorData.getEmail());
        instructor.setInfo(instructorData.getInfo());
        instructor.setUser(getInstructorUser(userId));

        instructorRepository.save(instructor);

        LOGGER.info("Created instructor with id: {}", instructor.getId());
        return instructorMapper.to(instructor.getId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public List<CourseOverviewDTO> courseList() throws SomethingWentWrongException {
        var instructor = getLoggedInstructor();

        return courseOverviewMapper.toList(instructor.getCourses());
    }

    private User getInstructorUser(Long userId) throws SomethingWentWrongException {
        User instructorUser = userService.findUserById(userId);

        if(!instructorUser.isInstructor()){
            throw USER_IS_NOT_INSTRUCTOR.ofException();
        }

        return instructorUser;
    }
}

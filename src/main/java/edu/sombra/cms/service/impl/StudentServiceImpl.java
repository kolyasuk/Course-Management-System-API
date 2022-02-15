package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.StudentCourseOverviewDTO;
import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.mapper.StudentCourseOverviewMapper;
import edu.sombra.cms.domain.mapper.StudentMapper;
import edu.sombra.cms.domain.payload.StudentData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.StudentRepository;
import edu.sombra.cms.service.StudentService;
import edu.sombra.cms.service.UserService;
import edu.sombra.cms.util.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static edu.sombra.cms.messages.StudentMessage.*;

@Service
@Validated
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final StudentCourseOverviewMapper studentCourseOverviewMapper;

    private static final LoggingService LOGGER = new LoggingService(StudentServiceImpl.class);

    @Override
    public Student getByStudentId(Long id) throws SomethingWentWrongException {
        return studentRepository.findById(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public Student getByUserId(Long id) throws SomethingWentWrongException {
        return studentRepository.findByUserId(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public Student getLoggedStudent() throws SomethingWentWrongException {
        var user = Optional.of(userService.getLoggedUser())
                .filter(User::isStudent).orElseThrow(USER_NOT_STUDENT::ofException);

        return getByUserId(user.getId());
    }

    @Override
    public List<Student> getByIdList(List<Long> ids) throws SomethingWentWrongException {
        return Optional.of(studentRepository.findAllById(ids)).filter(o -> !o.isEmpty())
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public StudentDTO create(@Valid StudentData studentData, Long userId) throws SomethingWentWrongException {
        if(studentRepository.findByUserId(userId).isPresent()){
            throw INFO_ALREADY_CREATED.ofException();
        }

        Student student = new Student();

        student.setFirstName(studentData.getFirstName());
        student.setLastName(studentData.getLastName());
        student.setEmail(studentData.getEmail());
        student.setUser(getStudentUser(userId));

        studentRepository.save(student);

        LOGGER.info("Created student {} {} with id: {}", student.getFirstName(), student.getLastName(), student.getId());
        return studentMapper.to(student);
    }

    @Override
    public List<StudentCourseOverviewDTO> courseList() throws SomethingWentWrongException {
        var student = getLoggedStudent();

        return studentCourseOverviewMapper.toList(student.getStudentCourses());
    }

    private User getStudentUser(Long userId) throws SomethingWentWrongException {
        if(userService.getLoggedUser().isAdmin()){
            if(userId == null)
                throw EMPTY_USER_ID.ofException();

            return userService.findUserById(userId);
        }

        return userService.getLoggedUser();
    }
}

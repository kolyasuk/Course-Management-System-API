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

    @Override
    public Student getById(Long id) throws SomethingWentWrongException {
        return studentRepository.findById(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public List<Student> getByIdList(List<Long> ids) throws SomethingWentWrongException {
        return Optional.of(studentRepository.findAllById(ids)).filter(o -> !o.isEmpty())
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public StudentDTO create(@Valid StudentData studentData, Long userId) throws SomethingWentWrongException {
        var user = getStudentUser(userId);

        if(user.getStudent() != null){
            throw INFO_ALREADY_CREATED.ofException();
        }

        Student student = new Student();

        student.setFirstName(studentData.getFirstName());
        student.setLastName(studentData.getLastName());
        student.setEmail(studentData.getEmail());
        student.setUser(user);

        return studentMapper.to(studentRepository.save(student));
    }

    @Override
    public List<StudentCourseOverviewDTO> courseList() throws SomethingWentWrongException {
        var loggedUser = userService.getLoggedUser();
        if(!loggedUser.isStudent())
            throw USER_NOT_STUDENT.ofException();

        return studentCourseOverviewMapper.toList(loggedUser.getStudent().getStudentCourses());
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

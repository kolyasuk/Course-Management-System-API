package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.StudentCourseOverviewDTO;
import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.mapper.StudentCourseOverviewMapper;
import edu.sombra.cms.domain.mapper.StudentMapper;
import edu.sombra.cms.domain.payload.StudentData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.StudentRepository;
import edu.sombra.cms.service.StudentService;
import edu.sombra.cms.service.UserService;
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Student getByStudentId(Long id) throws SomethingWentWrongException {
        return studentRepository.findById(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Student getByUserId(Long id) throws SomethingWentWrongException {
        return studentRepository.findByUserId(id).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Student getLoggedStudent() throws SomethingWentWrongException {
        return getByUserId(SecurityUtil.getLoggedUserId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public List<Student> getByIdList(List<Long> ids) throws SomethingWentWrongException {
        return Optional.of(studentRepository.findAllById(ids)).filter(o -> !o.isEmpty())
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public StudentDTO create(@Valid StudentData studentData, Long userId) throws SomethingWentWrongException {
        userId = Optional.ofNullable(userId).orElse(SecurityUtil.getLoggedUserId());

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
        return studentMapper.to(student.getId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public List<StudentCourseOverviewDTO> courseList() throws SomethingWentWrongException {
        var student = getLoggedStudent();
        return getStudentCourse(student.getStudentCourses());
    }

    private List<StudentCourseOverviewDTO> getStudentCourse(List<StudentCourse> studentCourses) throws SomethingWentWrongException {
        List<StudentCourseOverviewDTO> res = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            res.add(studentCourseOverviewMapper.to(studentCourse.getStudent().getId(), studentCourse.getCourse().getId()));
        }
        return res;
    }

    private User getStudentUser(Long userId) throws SomethingWentWrongException {
        User studentUser = userService.findUserById(userId);

        if(!studentUser.isStudent()){
            throw USER_IS_NOT_STUDENT.ofException();
        }

        return studentUser;
    }
}

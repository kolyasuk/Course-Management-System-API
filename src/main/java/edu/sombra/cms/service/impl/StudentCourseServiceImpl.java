package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.mapper.StudentCourseMapper;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.StudentCourseRepository;
import edu.sombra.cms.service.StudentCourseService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

import static edu.sombra.cms.messages.StudentCourseMessage.NOT_FOUND;
import static edu.sombra.cms.messages.StudentMessage.USER_NOT_STUDENT;

@Service
@Validated
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final UserService userService;
    private final StudentCourseMapper studentCourseMapper;

    @Override
    public StudentCourse getByStudentAndCourse(Long studentId, Long courseId) throws SomethingWentWrongException {
        return studentCourseRepository.findStudentCourseByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public StudentCourseDTO getDTOByCourseId(Long courseId) throws SomethingWentWrongException {
        var student = Optional.of(userService.getLoggedUser())
                .filter(User::isStudent).map(User::getStudent).orElseThrow(USER_NOT_STUDENT::ofException);

        var studentCourse = getByStudentAndCourse(student.getId(), courseId);
        return studentCourseMapper.to(studentCourse);
    }

    @Override
    public void feedback(Long courseId, @Valid StudentCourseFeedbackData studentCourseFeedbackData) throws SomethingWentWrongException {
        var studentCourse = getByStudentAndCourse(studentCourseFeedbackData.getStudentId(), courseId);

        studentCourse.setFeedback(studentCourseFeedbackData.getFeedback());
        studentCourseRepository.save(studentCourse);
    }
}

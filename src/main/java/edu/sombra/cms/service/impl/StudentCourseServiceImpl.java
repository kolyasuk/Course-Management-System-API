package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.mapper.StudentCourseMapper;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.repository.StudentCourseRepository;
import edu.sombra.cms.service.StudentCourseService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final UserService userService;
    private final StudentCourseMapper studentCourseMapper;

    @Override
    public StudentCourse getByStudentAndCourse(Long studentId, Long courseId) {
        return studentCourseRepository.findStudentCourseByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new IllegalArgumentException("StudentCourse is not found"));
    }

    @Override
    public StudentCourseDTO getDTOByCourseId(Long courseId) {
        var student = Optional.of(userService.getLoggedUser())
                .filter(User::isStudent).map(User::getStudent).orElseThrow(() -> new IllegalArgumentException("You are not student"));

        var studentCourse = getByStudentAndCourse(student.getId(), courseId);
        return studentCourseMapper.to(studentCourse);
    }

    @Override
    public void feedback(Long courseId, @Valid StudentCourseFeedbackData studentCourseFeedbackData) {
        var studentCourse = getByStudentAndCourse(studentCourseFeedbackData.getStudentId(), courseId);

        studentCourse.setFeedback(studentCourseFeedbackData.getFeedback());
        studentCourseRepository.save(studentCourse);
    }
}

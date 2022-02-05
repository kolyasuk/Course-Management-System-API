package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.repository.StudentCourseRepository;
import edu.sombra.cms.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;

    @Override
    public StudentCourse getByStudentAndCourse(Long studentId, Long courseId) {
        return studentCourseRepository.findStudentCourseByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new IllegalArgumentException("StudentCourse is not found"));
    }

    @Override
    public void feedback(@Valid StudentCourseFeedbackData studentCourseFeedbackData) {
        var studentCourse = getByStudentAndCourse(studentCourseFeedbackData.getStudentId(), studentCourseFeedbackData.getCourseId());

        studentCourse.setFeedback(studentCourseFeedbackData.getFeedback());
        studentCourseRepository.save(studentCourse);
    }
}

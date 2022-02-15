package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.mapper.StudentCourseMapper;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.StudentCourseRepository;
import edu.sombra.cms.service.StudentCourseService;
import edu.sombra.cms.service.StudentService;
import edu.sombra.cms.util.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import static edu.sombra.cms.messages.StudentCourseMessage.NOT_FOUND;

@Service
@Validated
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentService studentService;
    private final StudentCourseMapper studentCourseMapper;


    private static final LoggingService LOGGER = new LoggingService(StudentCourseServiceImpl.class);

    @Override
    public StudentCourse getByStudentAndCourse(Long studentId, Long courseId) throws SomethingWentWrongException {
        return studentCourseRepository.findStudentCourseByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public StudentCourseDTO getDTOByCourseId(Long courseId) throws SomethingWentWrongException {
        var student = studentService.getLoggedStudent();

        var studentCourse = getByStudentAndCourse(student.getId(), courseId);
        return studentCourseMapper.to(studentCourse);
    }

    @Override
    public void feedback(Long courseId, @Valid StudentCourseFeedbackData studentCourseFeedbackData) throws SomethingWentWrongException {
        var studentCourse = getByStudentAndCourse(studentCourseFeedbackData.getStudentId(), courseId);

        studentCourse.setFeedback(studentCourseFeedbackData.getFeedback());
        studentCourseRepository.save(studentCourse);

        LOGGER.info("Created feedback to student with id: {}, for course with id: {} ", studentCourseFeedbackData.getStudentId(), courseId);
    }
}

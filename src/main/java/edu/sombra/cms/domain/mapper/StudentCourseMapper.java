package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.dto.StudentLessonOverviewDTO;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.domain.enumeration.StudentCourseStatus;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentCourseMapper {

    private final StudentLessonOverviewMapper lessonOverviewMapper;
    @Lazy
    private final StudentCourseService studentCourseService;

    public StudentCourseDTO to(Long studentId, Long courseId) throws SomethingWentWrongException {
        var studentCourse = studentCourseService.getByStudentAndCourse(studentId, courseId);

        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
        var course = studentCourse.getCourse();

        studentCourseDTO.setId(course.getId());
        studentCourseDTO.setName(course.getName());
        studentCourseDTO.setMark(studentCourse.getMark());
        studentCourseDTO.setCourseStatus(course.getStatus());

        var studentCourseStatus = Optional.ofNullable(studentCourse.getMark()).map(StudentCourseStatus::ofMark).orElse(null);
        studentCourseDTO.setPassStatus(studentCourseStatus);

        studentCourseDTO.setStudentLessons(getStudentLessons(studentCourse.getStudent().getStudentLessons()));

        return studentCourseDTO;
    }

    private List<StudentLessonOverviewDTO> getStudentLessons(List<StudentLesson> studentLessons) throws SomethingWentWrongException {
        List<StudentLessonOverviewDTO> res = new ArrayList<>();
        for (StudentLesson studentLesson : studentLessons) {
            res.add(lessonOverviewMapper.to(studentLesson.getStudent().getId(), studentLesson.getLesson().getId()));
        }
        return res;
    }
}

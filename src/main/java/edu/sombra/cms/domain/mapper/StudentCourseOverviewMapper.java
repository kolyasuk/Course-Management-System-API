package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentCourseOverviewDTO;
import edu.sombra.cms.domain.enumeration.StudentCourseStatus;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentCourseOverviewMapper {

    @Lazy
    private final StudentCourseService studentCourseService;

    public StudentCourseOverviewDTO to(Long studentId, Long courseId) throws SomethingWentWrongException {
        var studentCourse = studentCourseService.getByStudentAndCourse(studentId, courseId);
        var course = studentCourse.getCourse();

        StudentCourseOverviewDTO studentCourseOverviewDTO = new StudentCourseOverviewDTO();

        studentCourseOverviewDTO.setId(course.getId());
        studentCourseOverviewDTO.setName(course.getName());
        studentCourseOverviewDTO.setMark(studentCourse.getMark());
        studentCourseOverviewDTO.setStatus(course.getStatus());

        var studentCourseStatus = Optional.ofNullable(studentCourse.getMark()).map(StudentCourseStatus::ofMark).orElse(null);
        studentCourseOverviewDTO.setPassStatus(studentCourseStatus);

        return studentCourseOverviewDTO;
    }

}

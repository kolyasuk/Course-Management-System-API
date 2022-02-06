package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.enumeration.StudentCourseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentCourseMapper extends AbstractMapper<StudentCourse, StudentCourseDTO> {

    private final StudentLessonOverviewMapper lessonOverviewMapper;

    @Override
    public StudentCourseDTO to(StudentCourse studentCourse){
        var course = studentCourse.getCourse();

        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();

        studentCourseDTO.setId(course.getId());
        studentCourseDTO.setName(course.getName());
        studentCourseDTO.setMark(studentCourse.getMark());
        studentCourseDTO.setCourseStatus(course.getStatus());

        var studentCourseStatus = Optional.ofNullable(studentCourse.getMark()).map(StudentCourseStatus::ofMark).orElse(null);
        studentCourseDTO.setPassStatus(studentCourseStatus);

        studentCourseDTO.setStudentLessons(lessonOverviewMapper.toList(studentCourse.getStudent().getStudentLessons()));

        return studentCourseDTO;
    }
}

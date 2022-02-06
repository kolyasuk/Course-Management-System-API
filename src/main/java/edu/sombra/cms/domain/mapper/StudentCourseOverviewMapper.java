package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentCourseOverviewDTO;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.enumeration.StudentCourseStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentCourseOverviewMapper extends AbstractMapper<StudentCourse, StudentCourseOverviewDTO> {

    @Override
    public StudentCourseOverviewDTO to(StudentCourse studentCourse){
        var course = studentCourse.getCourse();

        StudentCourseOverviewDTO studentCourseOverviewDTO = new StudentCourseOverviewDTO();

        studentCourseOverviewDTO.setId(course.getId());
        studentCourseOverviewDTO.setName(course.getName());
        studentCourseOverviewDTO.setCourseStatus(course.getStatus());

        var studentCourseStatus = Optional.ofNullable(studentCourse.getMark()).map(StudentCourseStatus::ofMark).orElse(null);
        studentCourseOverviewDTO.setPassStatus(studentCourseStatus);

        return studentCourseOverviewDTO;
    }
}

package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper extends AbstractMapper<Course, CourseDTO> {

    private final InstructorOverviewMapper instructorOverviewMapper;
    private final LessonOverviewMapper lessonOverviewMapper;
    private final StudentOverviewMapper studentOverviewMapper;

    public CourseDTO to(Course course){
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setStatus(course.getStatus());
        courseDTO.setInstructors(instructorOverviewMapper.toList(course.getInstructors()));
        courseDTO.setLessons(lessonOverviewMapper.toList(course.getLessons()));
        courseDTO.setStudents(studentOverviewMapper.toList(course.getStudents()));

        return courseDTO;
    }
}

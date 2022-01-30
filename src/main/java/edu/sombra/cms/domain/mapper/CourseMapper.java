package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper extends AbstractMapper<Course, CourseDTO> {

    private final OverviewInstructorMapper overviewInstructorMapper;
    private final LessonMapper lessonMapper;

    public CourseDTO to(Course course){
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setStatus(course.getStatus());
        courseDTO.setInstructors(overviewInstructorMapper.toList(course.getInstructors()));
        courseDTO.setLessons(lessonMapper.toList(course.getLessons()));

        return courseDTO;
    }
}

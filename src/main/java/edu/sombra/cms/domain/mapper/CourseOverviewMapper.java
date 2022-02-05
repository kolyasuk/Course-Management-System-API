package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseOverviewMapper extends AbstractMapper<Course, CourseOverviewDTO> {

    @Override
    public CourseOverviewDTO to(Course course){
        CourseOverviewDTO courseOverviewDTO = new CourseOverviewDTO();

        courseOverviewDTO.setId(course.getId());
        courseOverviewDTO.setName(course.getName());
        courseOverviewDTO.setStatus(course.getStatus());

        return courseOverviewDTO;
    }
}

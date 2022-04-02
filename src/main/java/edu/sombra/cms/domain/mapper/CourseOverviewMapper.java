package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseOverviewMapper extends AbstractMapper<Long, CourseOverviewDTO> {

    @Lazy
    private final CourseService courseService;

    @Override
    public CourseOverviewDTO to(Long id) throws SomethingWentWrongException {
        Course course = courseService.getById(id);
        CourseOverviewDTO courseOverviewDTO = new CourseOverviewDTO();

        courseOverviewDTO.setId(course.getId());
        courseOverviewDTO.setName(course.getName());
        courseOverviewDTO.setStatus(course.getStatus());

        return courseOverviewDTO;
    }

    public List<CourseOverviewDTO> toList(List<Course> courses) throws SomethingWentWrongException {
        return toList(courses.stream().map(Course::getId).collect(Collectors.toList()));
    }
}

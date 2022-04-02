package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper extends AbstractMapper<Long, CourseDTO> {

    @Lazy
    private final CourseService courseService;
    private final InstructorOverviewMapper instructorOverviewMapper;
    private final LessonOverviewMapper lessonOverviewMapper;
    private final StudentOverviewMapper studentOverviewMapper;

    public CourseDTO to(Long id) throws SomethingWentWrongException {
        Course course = courseService.getById(id);
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setStatus(course.getStatus());


        courseDTO.setInstructors(instructorOverviewMapper.toList(entitiesToIds(course.getInstructors())));
        courseDTO.setLessons(lessonOverviewMapper.toList(entitiesToIds(course.getLessons())));
        courseDTO.setStudents(studentOverviewMapper.toList(entitiesToIds(course.getStudents())));

        return courseDTO;
    }



}

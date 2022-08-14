package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.StudentCourseService;
import edu.sombra.cms.service.impl.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static edu.sombra.cms.service.impl.UserAccessService.AccessEntity.COURSE;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final StudentCourseService studentCourseService;
    private final UserAccessService userAccessService;

    @GetMapping("/{id}")
    public CourseDTO getById(@PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(COURSE, id);
        return courseService.getDTOById(id);
    }

    @PutMapping("/{id}")
    public CourseDTO update(@RequestBody @Valid CourseData courseData, @PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(COURSE, id);
        return courseService.update(id, courseData);
    }

    @PutMapping("/{id}/start")
    public void start(@PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(COURSE, id);
        courseService.start(id);
    }

    @PutMapping("/{id}/finish")
    public void finish(@PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(COURSE, id);
        courseService.finish(id);
    }

    @PutMapping("/{id}/feedback")
    public void studentCourseFeedback(@PathVariable("id") Long courseId, @RequestBody @Valid StudentCourseFeedbackData studentCourseFeedbackData) throws SomethingWentWrongException {
        userAccessService.checkAccess(COURSE, courseId);
        studentCourseService.feedback(courseId, studentCourseFeedbackData);
    }

    @GetMapping("/{id}/student")
    public List<StudentOverviewDTO> courseStudentList(@PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(COURSE, id);
        return courseService.courseStudentList(id);
    }
}

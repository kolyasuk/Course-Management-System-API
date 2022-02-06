package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final StudentCourseService studentCourseService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO getById(@PathVariable Long id){
        return courseService.getDTOById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO update(@RequestBody CourseData courseData, @PathVariable Long id){
        return courseService.update(id, courseData);
    }

    @PutMapping("/{id}/start")
    @ResponseStatus(HttpStatus.OK)
    public void start(@PathVariable Long id){
        courseService.start(id);
    }

    @PutMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void finish(@PathVariable Long id){
        courseService.finish(id);
    }

    @PutMapping("/{id}/feedback")
    @ResponseStatus(HttpStatus.OK)
    public void studentCourseFeedback(@PathVariable("id") Long courseId, @RequestBody StudentCourseFeedbackData studentCourseFeedbackData){
        studentCourseService.feedback(courseId, studentCourseFeedbackData);
    }

    @GetMapping("/{id}/student")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentOverviewDTO> courseStudentList(@PathVariable Long id){
        return courseService.courseStudentList(id);
    }
}

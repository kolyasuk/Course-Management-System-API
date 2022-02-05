package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.payload.InstructorData;
import edu.sombra.cms.domain.payload.StudentCourseFeedbackData;
import edu.sombra.cms.service.InstructorService;
import edu.sombra.cms.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;
    private final StudentCourseService studentCourseService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public InstructorDTO createInstructorInfo(@RequestBody InstructorData instructorData, @RequestParam(required = false) Long userId){
        return instructorService.create(instructorData, userId);
    }

    @PutMapping("/course/feedback")
    @ResponseStatus(HttpStatus.OK)
    public void studentCourseFeedback(@RequestBody StudentCourseFeedbackData studentCourseFeedbackData){
        studentCourseService.feedback(studentCourseFeedbackData);
    }

    @GetMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseOverviewDTO> courseList(){
        return instructorService.courseList();
    }

    @GetMapping("/course/{id}/student")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentOverviewDTO> courseStudentList(@PathVariable Long id){
        return instructorService.courseStudentList(id);
    }

}

package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.StudentCourseDTO;
import edu.sombra.cms.domain.dto.StudentCourseOverviewDTO;
import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.dto.StudentLessonDTO;
import edu.sombra.cms.domain.payload.HomeworkData;
import edu.sombra.cms.domain.payload.StudentData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentCourseService;
import edu.sombra.cms.service.StudentLessonService;
import edu.sombra.cms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    private final StudentCourseService studentCourseService;
    private final StudentLessonService studentLessonService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO createStudentInfo(@RequestBody @Valid StudentData studentData, @RequestParam(required = false) Long userId) throws SomethingWentWrongException {
        return studentService.create(studentData, userId);
    }

    @GetMapping("/validateUser")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCourseOverviewDTO> courseList() throws SomethingWentWrongException {
        return studentService.courseList();
    }

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentCourseDTO getStudentCourse(@PathVariable Long id) throws SomethingWentWrongException {
        return studentCourseService.getDTOByCourseId(id);
    }

    @GetMapping("/lesson/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentLessonDTO getStudentLesson(@PathVariable Long id) throws SomethingWentWrongException {
        return studentLessonService.getDTOByLessonId(id);
    }

    @PutMapping(value = "/lesson/{id}/homework", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public void createHomework(@PathVariable Long id, @RequestPart("homeworkData") HomeworkData homeworkData, @RequestPart(value = "homeworkFile") MultipartFile homeworkFile) throws SomethingWentWrongException {
        studentLessonService.addHomework(id, homeworkData, homeworkFile);
    }
}

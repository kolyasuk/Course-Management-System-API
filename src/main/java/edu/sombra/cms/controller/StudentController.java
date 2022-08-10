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
import edu.sombra.cms.service.impl.UserAccessService;
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static edu.sombra.cms.service.impl.UserAccessService.AccessEntity.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    private final StudentCourseService studentCourseService;
    private final StudentLessonService studentLessonService;
    private final UserAccessService userAccessService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO createStudentInfo(@RequestBody @Valid StudentData studentData, @RequestParam(required = false) Long userId) throws SomethingWentWrongException {
        userId = Optional.ofNullable(userId).orElse(SecurityUtil.getLoggedUserId());
        userAccessService.checkAccess(USER, userId);
        return studentService.create(studentData, userId);
    }

    @GetMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCourseOverviewDTO> courseList() throws SomethingWentWrongException {
        return studentService.courseList();
    }

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentCourseDTO getStudentCourse(@PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(STUDENT_COURSE, id);
        return studentCourseService.getDTOByCourseId(id);
    }

    @GetMapping("/lesson/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentLessonDTO getStudentLesson(@PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(STUDENT_LESSON, id);
        return studentLessonService.getDTOByLessonId(id);
    }

    @PostMapping(value = "/lesson/{id}/homework", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public void createHomework(@PathVariable Long id, @RequestPart("homeworkData") HomeworkData homeworkData, @RequestPart(value = "homeworkFile") MultipartFile homeworkFile) throws SomethingWentWrongException {
        userAccessService.checkAccess(STUDENT_LESSON, id);
        studentLessonService.addHomework(id, homeworkData, homeworkFile);
    }
}

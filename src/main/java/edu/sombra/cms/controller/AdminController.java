package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.enumeration.Role;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.domain.payload.UserRegistrationData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;

    @PostMapping("/user")
    public FullUserInfoDTO register(@RequestBody @Valid UserRegistrationData userRegistrationData) throws SomethingWentWrongException {
        return userService.create(userRegistrationData);
    }

    @PostMapping("/course")
    public CourseDTO createCourse(@RequestBody @Valid CourseData courseData) throws SomethingWentWrongException {
        return courseService.create(courseData);
    }

    @PutMapping("/course/{courseId}/instructor/assign")
    public CourseDTO assignInstructor(@PathVariable Long courseId, @RequestParam Long instructorId) throws SomethingWentWrongException {
        return courseService.assignInstructor(courseId, instructorId);
    }

    @PutMapping("/course/{courseId}/student/assign")
    public CourseDTO assignStudent(@PathVariable Long courseId, @RequestParam Long studentId) throws SomethingWentWrongException {
        return courseService.assignStudent(courseId, studentId);
    }

    @PutMapping("/user/role/{userId}")
    public void assignUserRole(@PathVariable Long userId, @RequestParam("role") Role role) throws SomethingWentWrongException {
        userService.setUserRole(userId, role);
    }

}

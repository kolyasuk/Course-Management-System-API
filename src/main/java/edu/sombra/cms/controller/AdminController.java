package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.domain.payload.RegistrationData;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;

    @PostMapping("/user")
    public FullUserInfoDTO register(@RequestBody RegistrationData registrationData) {
        return userService.create(registrationData);
    }

    @PostMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO createCourse(@RequestBody @Valid CourseData courseData){
        return courseService.create(courseData);
    }

    @PutMapping("/course/{courseId}/instructor/assign")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO assignInstructor(@PathVariable Long courseId, @RequestParam Long instructorId){
        return courseService.assignInstructor(courseId, instructorId);
    }

    @PutMapping("/course/{courseId}/student/assign")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO assignStudent(@PathVariable Long courseId, @RequestParam Long studentId){
        return courseService.assignStudent(courseId, studentId);
    }

    @PutMapping("/user/role/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void assignUserRole(@PathVariable Long userId, @RequestParam("role") RoleEnum role){
        userService.setUserRole(userId, role);
    }

}

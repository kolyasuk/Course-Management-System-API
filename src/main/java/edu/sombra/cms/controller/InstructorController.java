package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.payload.InstructorData;
import edu.sombra.cms.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public InstructorDTO createInstructorInfo(@RequestBody InstructorData instructorData, @RequestParam(required = false) Long userId){
        return instructorService.create(instructorData, userId);
    }

    @GetMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseOverviewDTO> courseList(){
        return instructorService.courseList();
    }

}

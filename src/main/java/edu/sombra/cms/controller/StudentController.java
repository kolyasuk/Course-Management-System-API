package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.StudentDTO;
import edu.sombra.cms.domain.payload.StudentData;
import edu.sombra.cms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO createStudentInfo(@RequestBody @Valid StudentData studentData, @RequestParam(required = false) Long userId){
        return studentService.create(studentData, userId);
    }

}

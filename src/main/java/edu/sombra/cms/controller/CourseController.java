package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO update(@RequestBody @Valid CourseData courseData, @PathVariable Long id){
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

}

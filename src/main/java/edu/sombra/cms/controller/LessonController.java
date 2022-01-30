package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.domain.payload.LessonData;
import edu.sombra.cms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LessonDTO create(@RequestBody @Valid LessonData lessonData){
        return lessonService.create(lessonData);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void evaluate(@RequestBody @Valid EvaluateLessonData evaluateLessonData){
        lessonService.evaluate(evaluateLessonData);
    }

}

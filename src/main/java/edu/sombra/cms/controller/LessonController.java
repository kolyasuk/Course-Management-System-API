package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.domain.payload.LessonData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.LessonService;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final StudentLessonService studentLessonService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDTO getById(@PathVariable Long id) throws SomethingWentWrongException {
        return lessonService.getDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LessonDTO create(@RequestBody LessonData lessonData) throws SomethingWentWrongException {
        return lessonService.create(lessonData);
    }

    @PutMapping("/{id}/mark")
    @ResponseStatus(HttpStatus.OK)
    public void evaluate(@PathVariable("id") Long lessonId, @RequestBody EvaluateLessonData evaluateLessonData) throws SomethingWentWrongException {
        studentLessonService.evaluate(lessonId, evaluateLessonData);
    }

}

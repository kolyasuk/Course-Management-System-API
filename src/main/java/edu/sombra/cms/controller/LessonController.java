package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.domain.payload.LessonData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.LessonService;
import edu.sombra.cms.service.StudentLessonService;
import edu.sombra.cms.service.impl.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static edu.sombra.cms.service.impl.UserAccessService.AccessEntity.LESSON;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final StudentLessonService studentLessonService;
    private final UserAccessService userAccessService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDTO getById(@PathVariable Long id) throws SomethingWentWrongException {
        userAccessService.checkAccess(LESSON, id);
        return lessonService.getDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LessonDTO create(@RequestBody @Valid LessonData lessonData) throws SomethingWentWrongException {
        return lessonService.create(lessonData);
    }

    @PutMapping("/{id}/mark")
    @ResponseStatus(HttpStatus.OK)
    public void evaluate(@PathVariable("id") Long lessonId, @RequestBody @Valid EvaluateLessonData evaluateLessonData) throws SomethingWentWrongException {
        userAccessService.checkAccess(LESSON, lessonId);
        studentLessonService.evaluate(lessonId, evaluateLessonData);
    }

}

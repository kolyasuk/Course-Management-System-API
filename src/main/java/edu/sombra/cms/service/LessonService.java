package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.payload.LessonData;

import javax.validation.Valid;

public interface LessonService {

    Lesson getById(Long id);

    LessonDTO create(@Valid LessonData lessonData);

    LessonDTO update(Long id, @Valid LessonData lessonData);

}

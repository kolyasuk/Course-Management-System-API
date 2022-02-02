package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.payload.LessonData;

public interface LessonService {

    Lesson getById(Long id);

    LessonDTO create(LessonData lessonData);

    LessonDTO update(Long id, LessonData lessonData);

}

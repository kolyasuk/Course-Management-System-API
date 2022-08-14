package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.dto.OverviewPageDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.payload.LessonData;
import edu.sombra.cms.messages.SomethingWentWrongException;

import javax.validation.Valid;
import java.util.List;

public interface LessonService {

    Lesson getById(Long id) throws SomethingWentWrongException;

    LessonDTO getDTOById(Long id) throws SomethingWentWrongException;

    LessonDTO create(@Valid LessonData lessonData) throws SomethingWentWrongException;

    LessonDTO update(Long id, @Valid LessonData lessonData);

    List<OverviewPageDTO.ComingLessonDTO> comingLessons() throws SomethingWentWrongException;

}

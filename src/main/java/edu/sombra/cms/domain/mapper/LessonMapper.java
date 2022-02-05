package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper extends AbstractMapper<Lesson, LessonDTO> {

    public LessonDTO to(Lesson lesson){
        LessonDTO lessonDTO = new LessonDTO();

        lessonDTO.setId(lesson.getId());
        lessonDTO.setName(lesson.getName());
        lessonDTO.setDescription(lesson.getDescription());

        return lessonDTO;
    }
}

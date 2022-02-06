package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.LessonOverviewDTO;
import edu.sombra.cms.domain.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonOverviewMapper extends AbstractMapper<Lesson, LessonOverviewDTO> {

    public static final int DESCRIPTION_PART_LENGTH = 100;

    public LessonOverviewDTO to(Lesson lesson){
        LessonOverviewDTO lessonOverviewDTO = new LessonOverviewDTO();

        lessonOverviewDTO.setId(lesson.getId());
        lessonOverviewDTO.setName(lesson.getName());
        lessonOverviewDTO.setDescriptionPart(createDescriptionPart(lesson.getDescription()));

        return lessonOverviewDTO;
    }

    private String createDescriptionPart(String description){
        if(description.length() > DESCRIPTION_PART_LENGTH){
            return description.substring(0, DESCRIPTION_PART_LENGTH) + "..";
        }

        return description;
    }
}

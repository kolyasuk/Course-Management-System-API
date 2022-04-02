package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.LessonOverviewDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonOverviewMapper extends AbstractMapper<Long, LessonOverviewDTO> {

    public static final int DESCRIPTION_PART_LENGTH = 100;

    @Lazy
    private final LessonService lessonService;

    public LessonOverviewDTO to(Long id) throws SomethingWentWrongException {
        Lesson lesson = lessonService.getById(id);

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

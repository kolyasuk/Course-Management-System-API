package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.OverviewPageDTO.ComingLessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class ComingLessonMapper extends AbstractMapper<Lesson, ComingLessonDTO> {

    public ComingLessonDTO to(Lesson lesson) {
        if(lesson.isComing()){
            return new ComingLessonDTO(lesson.getId(), lesson.getName(), LocalDate.now().until(lesson.getLessonDate(), ChronoUnit.DAYS));
        }
        return null;
    }

}

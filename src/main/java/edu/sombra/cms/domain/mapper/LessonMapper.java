package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.dto.StudentHomeworkDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonMapper extends AbstractMapper<Long, LessonDTO> {

    @Lazy
    private final LessonService lessonService;
    private final StudentHomeworkMapper studentHomeworkMapper;

    public LessonDTO to(Long id) throws SomethingWentWrongException {
        Lesson lesson = lessonService.getById(id);

        LessonDTO lessonDTO = new LessonDTO();

        lessonDTO.setId(lesson.getId());
        lessonDTO.setName(lesson.getName());
        lessonDTO.setDescription(lesson.getDescription());
        lessonDTO.setHomework(lesson.getHomework());
        lessonDTO.setStudentHomework(getStudentHomework(lesson.getStudentLessons()));

        return lessonDTO;
    }

    private List<StudentHomeworkDTO> getStudentHomework(List<StudentLesson> studentLessons) throws SomethingWentWrongException {
        List<StudentHomeworkDTO> res = new ArrayList<>();
        for (StudentLesson studentLesson : studentLessons) {
            res.add(studentHomeworkMapper.to(studentLesson.getStudent().getId(), studentLesson.getLesson().getId()));
        }
        return res;
    }
}

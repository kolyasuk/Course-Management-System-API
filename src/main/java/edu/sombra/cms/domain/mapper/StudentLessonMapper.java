package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentLessonDTO;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentLessonMapper {

    private final LessonMapper lessonMapper;
    @Lazy
    private final StudentLessonService studentLessonService;

    public StudentLessonDTO to(Long studentId, Long lessonId) throws SomethingWentWrongException {
        StudentLesson studentLesson = studentLessonService.getByStudentAndLesson(studentId, lessonId);

        StudentLessonDTO studentLessonDTO = new StudentLessonDTO();

        studentLessonDTO.setMark(studentLesson.getMark());
        studentLessonDTO.setNotes(studentLesson.getNotes());
        studentLessonDTO.setFeedback(studentLesson.getFeedback());

        studentLessonDTO.setLesson(lessonMapper.to(studentLesson.getLesson().getId()));

        return studentLessonDTO;
    }
}

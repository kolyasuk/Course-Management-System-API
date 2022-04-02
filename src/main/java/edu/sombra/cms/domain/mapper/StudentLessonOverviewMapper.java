package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentLessonOverviewDTO;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentLessonOverviewMapper {

    private final LessonOverviewMapper lessonOverviewMapper;
    @Lazy
    private final StudentLessonService studentLessonService;


    public StudentLessonOverviewDTO to(Long studentId, Long lessonId) throws SomethingWentWrongException {
        StudentLesson studentLesson = studentLessonService.getByStudentAndLesson(studentId, lessonId);

        StudentLessonOverviewDTO studentLessonOverviewDTO = new StudentLessonOverviewDTO();

        studentLessonOverviewDTO.setMark(studentLesson.getMark());
        studentLessonOverviewDTO.setLesson(lessonOverviewMapper.to(studentLesson.getLesson().getId()));

        return studentLessonOverviewDTO;
    }
}

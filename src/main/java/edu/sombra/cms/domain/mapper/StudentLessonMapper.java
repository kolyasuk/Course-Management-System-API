package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentLessonDTO;
import edu.sombra.cms.domain.entity.StudentLesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentLessonMapper extends AbstractMapper<StudentLesson, StudentLessonDTO> {

    private final LessonMapper lessonMapper;

    @Override
    public StudentLessonDTO to(StudentLesson studentLesson){
        StudentLessonDTO studentLessonDTO = new StudentLessonDTO();

        studentLessonDTO.setMark(studentLesson.getMark());
        studentLessonDTO.setNotes(studentLesson.getNotes());
        studentLessonDTO.setFeedback(studentLesson.getFeedback());

        studentLessonDTO.setLesson(lessonMapper.to(studentLesson.getLesson()));

        return studentLessonDTO;
    }
}

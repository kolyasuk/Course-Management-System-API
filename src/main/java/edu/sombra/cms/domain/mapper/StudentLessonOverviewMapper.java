package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentLessonOverviewDTO;
import edu.sombra.cms.domain.entity.StudentLesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentLessonOverviewMapper extends AbstractMapper<StudentLesson, StudentLessonOverviewDTO> {

    private final LessonOverviewMapper lessonOverviewMapper;

    @Override
    public StudentLessonOverviewDTO to(StudentLesson studentLesson){
        StudentLessonOverviewDTO studentLessonOverviewDTO = new StudentLessonOverviewDTO();

        studentLessonOverviewDTO.setMark(studentLesson.getMark());
        studentLessonOverviewDTO.setLesson(lessonOverviewMapper.to(studentLesson.getLesson()));

        return studentLessonOverviewDTO;
    }
}

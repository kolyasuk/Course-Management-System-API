package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentHomeworkDTO;
import edu.sombra.cms.domain.entity.StudentLesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHomeworkMapper extends AbstractMapper<StudentLesson, StudentHomeworkDTO> {

    private final StudentOverviewMapper studentOverviewMapper;

    @Override
    public StudentHomeworkDTO to(StudentLesson studentLesson){
        StudentHomeworkDTO studentHomeworkDTO = new StudentHomeworkDTO();

        studentHomeworkDTO.setStudent(studentOverviewMapper.to(studentLesson.getStudent()));
        studentHomeworkDTO.setNotes(studentLesson.getNotes());
        studentHomeworkDTO.setFeedback(studentLesson.getFeedback());
        studentHomeworkDTO.setMark(studentLesson.getMark());
        studentHomeworkDTO.setFile("studentLesson.getHomeworkFile()");

        return studentHomeworkDTO;
    }
}

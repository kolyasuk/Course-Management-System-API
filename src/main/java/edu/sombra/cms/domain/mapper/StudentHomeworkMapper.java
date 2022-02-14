package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentHomeworkDTO;
import edu.sombra.cms.domain.entity.S3File;
import edu.sombra.cms.domain.entity.StudentLesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

        Optional.ofNullable(studentLesson.getHomeworkFile()).map(S3File::getFileKey).map(studentHomeworkDTO::setFileKey);
        Optional.ofNullable(studentLesson.getHomeworkFile()).map(S3File::getFilename).map(studentHomeworkDTO::setFilename);

        return studentHomeworkDTO;
    }
}

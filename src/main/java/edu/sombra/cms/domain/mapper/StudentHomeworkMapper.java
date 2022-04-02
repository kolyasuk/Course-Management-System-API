package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.StudentHomeworkDTO;
import edu.sombra.cms.domain.entity.S3File;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentHomeworkMapper {

    private final StudentOverviewMapper studentOverviewMapper;
    @Lazy
    private final StudentLessonService studentLessonService;

    public StudentHomeworkDTO to(Long studentId, Long lessonId) throws SomethingWentWrongException {
        StudentLesson studentLesson = studentLessonService.getByStudentAndLesson(studentId, lessonId);

        StudentHomeworkDTO studentHomeworkDTO = new StudentHomeworkDTO();

        studentHomeworkDTO.setStudent(studentOverviewMapper.to(studentLesson.getStudent().getId()));
        studentHomeworkDTO.setNotes(studentLesson.getNotes());
        studentHomeworkDTO.setFeedback(studentLesson.getFeedback());
        studentHomeworkDTO.setMark(studentLesson.getMark());

        Optional.ofNullable(studentLesson.getHomeworkFile()).map(S3File::getFileKey).map(studentHomeworkDTO::setFileKey);
        Optional.ofNullable(studentLesson.getHomeworkFile()).map(S3File::getFilename).map(studentHomeworkDTO::setFilename);

        return studentHomeworkDTO;
    }
}

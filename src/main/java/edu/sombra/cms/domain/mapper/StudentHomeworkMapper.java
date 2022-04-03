package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.FileDTO;
import edu.sombra.cms.domain.dto.StudentHomeworkDTO;
import edu.sombra.cms.domain.entity.S3File;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        studentHomeworkDTO.setHomeworks(getHomeworkFiles(studentLesson));

        return studentHomeworkDTO;
    }

    private List<FileDTO> getHomeworkFiles(StudentLesson studentLesson) {
        List<FileDTO> homeworks = new ArrayList<>();
        for (S3File homeworkFile : studentLesson.getHomeworkFiles()) {
            homeworks.add(FileDTO.of(homeworkFile));
        }

        return homeworks;
    }
}

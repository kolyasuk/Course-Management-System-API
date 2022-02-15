package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.StudentLessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.domain.mapper.StudentLessonMapper;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.domain.payload.HomeworkData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.StudentLessonRepository;
import edu.sombra.cms.service.HomeworkUploadService;
import edu.sombra.cms.service.StudentLessonService;
import edu.sombra.cms.service.StudentService;
import edu.sombra.cms.util.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.sombra.cms.messages.StudentLessonMessage.*;

@Service
@Validated
@RequiredArgsConstructor
public class StudentLessonServiceImpl implements StudentLessonService {

    private final StudentLessonRepository studentLessonRepository;
    private final StudentService studentService;
    private final StudentLessonMapper lessonMapper;
    private final HomeworkUploadService homeworkUploadService;

    private static final LoggingService LOGGER = new LoggingService(StudentLessonServiceImpl.class);

    @Override
    public StudentLesson getByStudentAndLesson(Long studentId, Long lessonId) throws SomethingWentWrongException {
        return studentLessonRepository.findStudentLessonByStudentIdAndLessonId(studentId, lessonId)
                .orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public StudentLesson getByLessonId(Long lessonId) throws SomethingWentWrongException {
        var student = studentService.getLoggedStudent();

        return getByStudentAndLesson(student.getId(), lessonId);
    }

    @Override
    public StudentLessonDTO getDTOByLessonId(Long lessonId) throws SomethingWentWrongException {
        return lessonMapper.to(getByLessonId(lessonId));
    }

    @Override
    public void saveStudentLessons(List<Lesson> lessons, Student student) {
        var studentLessons = lessons.stream().map(l -> new StudentLesson(student, l)).collect(Collectors.toList());
        studentLessonRepository.saveAll(studentLessons);

        LOGGER.info("Created lessons for student with id: {}", student.getId());
    }

    @Override
    public void saveStudentLessons(Lesson lesson, List<Student> students) {
        var studentLessons = students.stream().map(s -> new StudentLesson(s, lesson)).collect(Collectors.toList());
        studentLessonRepository.saveAll(studentLessons);

        LOGGER.info("Created lesson with id: {} for all related students", lesson.getId());
    }

    @Override
    public void evaluate(Long lessonId, @Valid EvaluateLessonData evaluateLessonData) throws SomethingWentWrongException {
        var studentLesson = Optional.of(getByStudentAndLesson(evaluateLessonData.getStudentId(), lessonId))
                .filter(s -> s.getHomeworkFile() == null).orElseThrow(HOMEWORK_IS_NOT_UPLOADED::ofException);

        if(studentLesson.getMark() == null && studentLesson.getHomeworkFile() != null){
            studentLesson.setMark(evaluateLessonData.getMark());
            studentLesson.setFeedback(evaluateLessonData.getFeedback());
            studentLessonRepository.save(studentLesson);
        }

        LOGGER.info("Evaluated lesson with id: {} for student with id: {}", lessonId, evaluateLessonData.getStudentId());
    }

    @Override
    public void addHomework(Long lessonId, HomeworkData homeworkData, MultipartFile homeworkFile) throws SomethingWentWrongException {
        var studentLesson = Optional.of(getByLessonId(lessonId))
                .filter(s -> s.getHomeworkFile() == null).orElseThrow(HOMEWORK_IS_ALREADY_UPLOADED::ofException);

        var s3file = homeworkUploadService.uploadStudentHomework(studentLesson.getStudent(), homeworkFile);
        s3file.map(studentLesson::setHomeworkFile).orElseThrow(UNABLE_TO_UPLOAD_HOMEWORK::ofException);

        studentLesson.setNotes(homeworkData.getNote());

        studentLessonRepository.save(studentLesson);
        LOGGER.info("Added homework to lesson with id: {} for student with id: {}", lessonId, studentLesson.getStudent().getId());
    }
}

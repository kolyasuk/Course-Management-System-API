package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.repository.StudentLessonRepository;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentLessonServiceImpl implements StudentLessonService {

    private final StudentLessonRepository studentLessonRepository;

    @Override
    public StudentLesson getByStudentAndLesson(Long studentId, Long lessonId) {
        return studentLessonRepository.findStudentLessonByStudentIdAndLessonId(studentId, lessonId)
                .orElseThrow(() -> new IllegalArgumentException("StudentLesson not found"));
    }

    @Override
    public void saveStudentLessons(List<Lesson> lessons, Student student) {
        var studentLessons = lessons.stream().map(l -> new StudentLesson(student, l)).collect(Collectors.toList());
        studentLessonRepository.saveAll(studentLessons);
    }

    @Override
    public void saveStudentLessons(Lesson lesson, List<Student> students) {
        var studentLessons = students.stream().map(s -> new StudentLesson(s, lesson)).collect(Collectors.toList());
        studentLessonRepository.saveAll(studentLessons);
    }

    @Override
    public void evaluate(EvaluateLessonData evaluateLessonData) {
        var studentLesson = getByStudentAndLesson(evaluateLessonData.getStudentId(), evaluateLessonData.getLessonId());

        if(studentLesson.getMark() == null && studentLesson.getHomeworkFile() != null){
            studentLesson.setMark(evaluateLessonData.getMark());
            studentLesson.setFeedback(evaluateLessonData.getFeedback());
            studentLessonRepository.save(studentLesson);
        }
    }

}

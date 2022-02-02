package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.repository.LessonRepository;
import edu.sombra.cms.repository.StudentLessonRepository;
import edu.sombra.cms.service.StudentLessonService;
import edu.sombra.cms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentLessonServiceImpl implements StudentLessonService {

    private final StudentLessonRepository studentLessonRepository;
    private final StudentService studentService;
    private final LessonRepository lessonRepository;

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
        var student = studentService.getById(evaluateLessonData.getStudentId());
        var lesson = lessonRepository.findById(evaluateLessonData.getLessonId()).orElseThrow(() -> new IllegalArgumentException("Lesson NF"));

        var studentLesson = studentLessonRepository.findStudentLessonByStudentAndLesson(student, lesson)
                .orElseThrow(() -> new IllegalArgumentException("StudentLesson not found"));

        if(studentLesson.getMark() == null && studentLesson.getHomeworkFile() != null){
            studentLesson.setMark(evaluateLessonData.getMark());
            studentLessonRepository.save(studentLesson);
        }
    }

}

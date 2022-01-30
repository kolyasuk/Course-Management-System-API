package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.domain.mapper.LessonMapper;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.domain.payload.LessonData;
import edu.sombra.cms.repository.LessonRepository;
import edu.sombra.cms.repository.StudentLessonRepository;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.LessonService;
import edu.sombra.cms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseService courseService;
    private final StudentLessonRepository studentLessonRepository;
    private final StudentService studentService;

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("lessonId incorrect"));
    }

    @Override
    public LessonDTO create(LessonData lessonData) {
        Lesson lesson = new Lesson();

        lesson.setName(lessonData.getName());
        lesson.setDescription(lessonData.getDescription());
        lesson.setCourse(courseService.getById(lessonData.getCourseId()));

        var result = lessonRepository.save(lesson);

        saveStudentLessons(lesson, result.getCourse().getStudents());

        return lessonMapper.to(result);
    }

    @Override
    public LessonDTO update(Long id, LessonData lessonData) {
        return null;
    }

    @Override
    public void evaluate(EvaluateLessonData evaluateLessonData) {
        var student = studentService.getById(evaluateLessonData.studentId);
        var lesson = getById(evaluateLessonData.getLessonId());

        var test = studentLessonRepository.findStudentLessonByStudentAndLesson(student, lesson)
                .orElseThrow(() -> new IllegalArgumentException("StudentLesson not found"));

//        test.
//        System.out.println(test);
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
}

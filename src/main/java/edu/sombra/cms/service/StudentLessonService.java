package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.StudentLessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.domain.payload.EvaluateLessonData;

import javax.validation.Valid;
import java.util.List;

public interface StudentLessonService {

    StudentLesson getByStudentAndLesson(Long studentId, Long lessonId);

    StudentLessonDTO getDTOByLessonId(Long lessonId);

    void saveStudentLessons(List<Lesson> lessons, Student student);

    void saveStudentLessons(Lesson lesson, List<Student> students);

    void evaluate(Long lessonId, @Valid EvaluateLessonData evaluateLessonData);

}

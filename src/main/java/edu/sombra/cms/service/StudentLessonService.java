package edu.sombra.cms.service;

import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.payload.EvaluateLessonData;

import java.util.List;

public interface StudentLessonService {

    void saveStudentLessons(List<Lesson> lessons, Student student);

    void saveStudentLessons(Lesson lesson, List<Student> students);

    void evaluate(EvaluateLessonData evaluateLessonData);

}

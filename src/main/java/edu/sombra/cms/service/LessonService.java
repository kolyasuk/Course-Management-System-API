package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.payload.EvaluateLessonData;
import edu.sombra.cms.domain.payload.LessonData;

import java.util.List;

public interface LessonService {

    Lesson getById(Long id);

    LessonDTO create(LessonData lessonData);

    LessonDTO update(Long id, LessonData lessonData);

    void evaluate(EvaluateLessonData evaluateLessonData);

    void saveStudentLessons(List<Lesson> lessons, Student student);

    void saveStudentLessons(Lesson lesson, List<Student> students);

}

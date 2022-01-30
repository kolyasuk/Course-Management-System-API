package edu.sombra.cms.repository;

import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.StudentLesson;
import edu.sombra.cms.domain.entity.StudentLessonPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLesson, StudentLessonPK> {

    Optional<StudentLesson> findStudentLessonByStudentAndLesson(Student student, Lesson lesson);

}

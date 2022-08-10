package edu.sombra.cms.repository;

import edu.sombra.cms.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLesson, StudentLesson.StudentLessonPK> {

    @Query("select sl.lesson.id from StudentLesson sl where sl.homeworkFiles in (:s3File)")
    Long getLessonIdByHomeworkFile(S3File s3File);

    Optional<StudentLesson> findStudentLessonByStudentIdAndLessonId(Long studentId, Long lessonId);

    Optional<StudentLesson> findStudentLessonByStudentUserIdAndLessonId(Long userId, Long lessonId);

    @Query("select case when count(sl) > 0 then true else false end " +
            "from StudentLesson sl where sl.student = :student and sl.lesson.course = :course and sl.mark is null")
    boolean existsStudentLessonByStudentAndCourseAndMarkIsNull(Student student, Course course);

    @Query("SELECT avg(sl.mark) FROM StudentLesson sl where sl.student = :student and sl.lesson.course = :course")
    Integer getAvgMark(Student student, Course course);

}

package edu.sombra.cms.repository;


import edu.sombra.cms.domain.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    //created native query only to create jpa test case
    @Query(value = "select count(*) " +
            "from Lesson l " +
            "join course c on c.id = l.course_id " +
            "where c.id = :courseId and l.homework_finish_date > current_date", nativeQuery = true)
    Integer existsNotFinishedLessons(Long courseId);
}

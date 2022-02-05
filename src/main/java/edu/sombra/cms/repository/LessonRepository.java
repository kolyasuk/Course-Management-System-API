package edu.sombra.cms.repository;


import edu.sombra.cms.domain.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select case when count(l) > 0 then true else false end " +
            "from Lesson l where l.course.id = :courseId and l.date > :date")
    boolean existsNotFinishedLessons(Long courseId, LocalDate date);
}

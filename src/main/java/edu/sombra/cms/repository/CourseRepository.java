package edu.sombra.cms.repository;


import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.domain.enumeration.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByIdAndStatus(Long id, CourseStatus status);

    Optional<Course> findTopByOrderByIdDesc();

    @Modifying
    @Query("update Course c set c.status = :status where c = :course")
    void setStatus(Course course, CourseStatus status);
}

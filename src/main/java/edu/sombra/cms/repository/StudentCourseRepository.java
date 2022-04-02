package edu.sombra.cms.repository;

import edu.sombra.cms.domain.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourse.StudentCoursePK> {

    Optional<StudentCourse> findStudentCourseByStudentIdAndCourseId(Long studentId, Long courseId);

    @Modifying
    @Query("update StudentCourse sc set sc.mark = :mark where sc = :studentCourse")
    void updateMark(Integer mark, StudentCourse studentCourse);

}

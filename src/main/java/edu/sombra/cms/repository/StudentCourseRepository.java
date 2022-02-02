package edu.sombra.cms.repository;

import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.entity.StudentCoursePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCoursePK> {

    @Modifying
    @Query("update StudentCourse sc set sc.mark = :mark where sc = :studentCourse")
    void updateMark(Integer mark, StudentCourse studentCourse);

}

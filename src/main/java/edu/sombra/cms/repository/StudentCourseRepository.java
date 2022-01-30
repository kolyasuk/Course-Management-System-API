package edu.sombra.cms.repository;

import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.entity.StudentCoursePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCoursePK> {

}

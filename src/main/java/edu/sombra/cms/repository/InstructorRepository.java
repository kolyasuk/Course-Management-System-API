package edu.sombra.cms.repository;


import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.entity.S3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

	Optional<Instructor> findById(Long id);

	Optional<Instructor> findByUserId(Long userId);

	@Query("select c.instructors from Course c inner join c.lessons l inner join l.studentLessons sl where sl.homeworkFile = :homeworkFile")
	List<Instructor> findInstructorsByHomeworkFile(S3File homeworkFile);


}

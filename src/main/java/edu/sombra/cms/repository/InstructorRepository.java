package edu.sombra.cms.repository;


import edu.sombra.cms.domain.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

	Optional<Instructor> findById(Long id);


}

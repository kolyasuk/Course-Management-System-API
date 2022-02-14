package edu.sombra.cms.repository;

import edu.sombra.cms.domain.entity.S3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface S3FileRepository extends JpaRepository<S3File, Long> {

    Optional<S3File> findByFileKey(String fileKey);

}

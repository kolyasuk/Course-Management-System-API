package edu.sombra.cms.repository;

import edu.sombra.cms.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "select * from user " +
            "join user_roles ur on user.id = ur.user_id " +
            "join role r on r.id = ur.role_id " +
            "and  r.name = :role",nativeQuery = true)
    Optional<List<User>> findAllByRoles(String role);

}

package edu.sombra.cms.repository;


import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(RoleEnum name);
}

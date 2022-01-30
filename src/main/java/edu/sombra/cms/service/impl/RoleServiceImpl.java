package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.entity.Role;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.repository.RoleRepository;
import edu.sombra.cms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(RoleEnum roleEnum) {
        return roleRepository.findByRole(roleEnum)
                .orElseThrow(() ->
                        new IllegalArgumentException("Role not found"));
    }
}

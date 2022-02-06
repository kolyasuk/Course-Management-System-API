package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.entity.Role;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.RoleRepository;
import edu.sombra.cms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static edu.sombra.cms.messages.RoleMessage.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(RoleEnum roleEnum) throws SomethingWentWrongException {
        return roleRepository.findByRole(roleEnum).orElseThrow(NOT_FOUND::ofException);
    }
}

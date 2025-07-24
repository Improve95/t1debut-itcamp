package ru.improve.itcamp.auth.service.core.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.core.repository.RoleRepository;
import ru.improve.itcamp.auth.service.core.service.RoleService;
import ru.improve.itcamp.auth.service.model.Role;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.NOT_FOUND;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleServiceImpl roleService;

    public RoleServiceImpl(RoleRepository roleRepository, @Lazy RoleServiceImpl roleService) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @Override
    public Role getRole(String name) {
        return roleService.findRole(name);
    }

    @Transactional
    public Role findRole(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "role", "id"));
    }

    @Transactional
    public Role findRole(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "role", "email"));
    }
}

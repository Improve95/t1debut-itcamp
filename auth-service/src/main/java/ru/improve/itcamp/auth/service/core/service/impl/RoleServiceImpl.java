package ru.improve.itcamp.auth.service.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.core.repository.RoleRepository;
import ru.improve.itcamp.auth.service.core.service.RoleService;
import ru.improve.itcamp.auth.service.model.Role;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Role getRole(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "role", "id"));
    }

    @Transactional
    @Override
    public Role getRole(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "role", "email"));
    }
}

package ru.improve.itcamp.auth.service.core.service;

import ru.improve.itcamp.auth.service.model.Role;

public interface RoleService {

    Role getRole(int id);

    Role getRole(String name);
}

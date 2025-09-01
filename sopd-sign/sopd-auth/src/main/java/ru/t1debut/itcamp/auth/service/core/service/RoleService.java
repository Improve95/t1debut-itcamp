package ru.t1debut.itcamp.auth.service.core.service;

import ru.t1debut.itcamp.auth.service.model.Role;

public interface RoleService {

    Role getRole(int id);

    Role getRole(String name);
}

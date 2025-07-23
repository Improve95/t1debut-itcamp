package ru.improve.itcamp.auth.service.util.mapper;

import org.mapstruct.Named;
import ru.improve.itcamp.auth.service.model.Role;
import ru.improve.itcamp.auth.service.model.User;

import java.util.Set;
import java.util.stream.Collectors;

@Named(MapperUtil.MAPPER_UTIL_NAME)
public final class MapperUtil {

    public static final String MAPPER_UTIL_NAME = "MapperUtil";

    public static final String GET_ROLES_ID_FUNC_NAME = "getRolesId";

    @Named(GET_ROLES_ID_FUNC_NAME)
    public static Set<Integer> getRolesId(User user) {
        return user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }
}

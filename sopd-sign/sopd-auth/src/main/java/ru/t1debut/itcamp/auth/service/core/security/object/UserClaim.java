package ru.t1debut.itcamp.auth.service.core.security.object;

import lombok.Data;
import ru.t1debut.itcamp.auth.service.model.User;

@Data
public class UserClaim {

    public UserClaim(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
    }

    private int id;

    private String email;

    private String firstName;
}

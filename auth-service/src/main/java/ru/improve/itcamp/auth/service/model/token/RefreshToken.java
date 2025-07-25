package ru.improve.itcamp.auth.service.model.token;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.improve.itcamp.auth.service.model.User;

@Entity
@Data
@Table(name = "white_list_refresh_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken implements TokenInterface {

    @Id
    protected String token;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    protected User user;
}

package ru.improve.itcamp.auth.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "white_list_access_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {

    @Id
    private String token;
}

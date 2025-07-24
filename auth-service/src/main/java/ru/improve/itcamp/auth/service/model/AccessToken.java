package ru.improve.itcamp.auth.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "white_list_access_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessToken  {

    @Id
    protected String token;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    protected User user;
}

package ru.t1debut.itcamp.consent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.t1debut.itcamp.consent.model.consent.Consent;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidate_profiles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfile {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "third_name")
    private String thirdName;

    private LocalDate birthdate;

    private String phone;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "candidateProfile")
    private List<Consent> consents;
}

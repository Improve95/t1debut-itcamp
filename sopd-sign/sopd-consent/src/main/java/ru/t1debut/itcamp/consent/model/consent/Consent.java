package ru.t1debut.itcamp.consent.model.consent;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.t1debut.itcamp.consent.model.CandidateProfile;
import ru.t1debut.itcamp.consent.model.SopdDocument;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "consents")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Consent {

    @Id
    private UUID id;

    @Column(
            name = "sopd_document_version",
            insertable = false,
            updatable = false
    )
    private int sopdDocumentVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "version")
    private SopdDocument sopdDocument;

    @Column(name = "manager_id")
    private int managerId;

    @Column(
            name = "candidate_profile_id",
            insertable = false,
            updatable = false
    )
    private Integer candidateProfileId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(referencedColumnName = "id")
    private CandidateProfile candidateProfile;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ConsentStatus status = ConsentStatus.WAITING;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}

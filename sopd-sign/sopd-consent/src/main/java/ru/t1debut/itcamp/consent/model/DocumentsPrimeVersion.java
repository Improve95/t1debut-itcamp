package ru.t1debut.itcamp.consent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documents_prime_version")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsPrimeVersion {

    @Id
    private int id;

    @OneToOne
    @JoinColumn(
            name = "sopd_document_prime_version",
            referencedColumnName = "version"
    )
    private SopdDocument primeSopdDocument;

    @OneToOne
    @JoinColumn(
            name = "email_form_prime_version",
            referencedColumnName = "version"
    )
    private EmailForm primeEmailForm;
}

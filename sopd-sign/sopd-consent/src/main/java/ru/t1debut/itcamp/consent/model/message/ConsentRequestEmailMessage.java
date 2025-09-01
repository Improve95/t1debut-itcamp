package ru.t1debut.itcamp.consent.model.message;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
import ru.t1debut.itcamp.consent.model.consent.Consent;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sopd_request_email_messages")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsentRequestEmailMessage {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "consent",
            referencedColumnName = "id"
    )
    private Consent consent;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EmailMessageStatus status = EmailMessageStatus.WAITING;

    @CreationTimestamp
    private Instant createdAt;
}

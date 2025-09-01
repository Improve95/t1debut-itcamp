package ru.t1debut.itcamp.notification.service.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "email_message")
public class EmailMessage {
    /**
     * Id письма
     */
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * Email адрес отправителя
     */
    @Column(name = "from_email", nullable = false)
    private String from;

    /**
     * Email адрес получателя
     */
    @Column(name = "to_email", nullable = false)
    private String to;

    /**
     * Заголовок письма
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Тело письма
     */
    @Column(name = "body", nullable = false)
    private String body;

    /**
     * Статус отправки письма
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmailStatus status;

}

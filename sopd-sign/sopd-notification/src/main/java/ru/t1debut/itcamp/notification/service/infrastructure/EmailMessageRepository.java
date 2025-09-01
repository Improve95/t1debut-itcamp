package ru.t1debut.itcamp.notification.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1debut.itcamp.notification.service.domain.EmailMessage;

import java.util.UUID;
@Repository
public interface EmailMessageRepository extends JpaRepository<EmailMessage, UUID> {

}

package ru.t1debut.itcamp.consent.core.service.implement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.core.dao.repository.ConsentRequestEmailMessageRepository;
import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEmailMessageStatusEvent;
import ru.t1debut.itcamp.consent.core.service.EmailMessageService;
import ru.t1debut.itcamp.consent.model.consent.Consent;
import ru.t1debut.itcamp.consent.model.message.ConsentRequestEmailMessage;

import java.util.UUID;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailMessageServiceImpl implements EmailMessageService {

    private final ConsentRequestEmailMessageRepository repository;

    @Transactional
    @Override
    public ConsentRequestEmailMessage getLastEmailMessage(Consent consent) {
        return repository.findByConsent(consent, Sort.by(Sort.Direction.DESC, "createdAt"), Limit.of(1));
    }

    @Transactional
    @Override
    public ConsentRequestEmailMessage save(ConsentRequestEmailMessage consentRequestEmailMessage) {
        return repository.save(consentRequestEmailMessage);
    }

    @Transactional
    @Override
    public void setMessageStatus(ConsentRequestEmailMessageStatusEvent consentRequestEmailMessageStatusEvent) {
        findConsentRequestEmailMessage(consentRequestEmailMessageStatusEvent.getMessageId())
                .setStatus(consentRequestEmailMessageStatusEvent.getStatus());
    }

    @Transactional
    @Override
    public ConsentRequestEmailMessage findConsentRequestEmailMessage(UUID id) {
        log.debug("findConsentRequestEmailMessage: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "ConsentRequestEmailMessage", "id"));
    }
}

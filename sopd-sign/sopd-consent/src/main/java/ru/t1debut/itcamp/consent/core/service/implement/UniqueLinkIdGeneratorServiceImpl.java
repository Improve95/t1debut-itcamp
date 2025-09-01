package ru.t1debut.itcamp.consent.core.service.implement;

import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.core.service.UniqueLinkIdGeneratorService;

import java.util.UUID;

@Service
public class UniqueLinkIdGeneratorServiceImpl implements UniqueLinkIdGeneratorService {

    @Override
    public UUID getRandomUuid() {
        return UUID.randomUUID();
    }
}

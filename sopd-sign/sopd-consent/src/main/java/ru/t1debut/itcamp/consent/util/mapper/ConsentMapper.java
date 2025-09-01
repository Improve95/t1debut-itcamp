package ru.t1debut.itcamp.consent.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.t1debut.itcamp.consent.api.dto.consent.ConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentResponse;
import ru.t1debut.itcamp.consent.model.consent.Consent;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = MapperUtil.class
)
public interface ConsentMapper {

    ConsentResponse toConsentResponse(Consent consent);

    SendConsentResponse toSendConsentResponse(Consent consent);
}

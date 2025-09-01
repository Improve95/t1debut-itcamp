package ru.t1debut.itcamp.consent.core.service;

import org.springframework.core.io.Resource;
import ru.t1debut.itcamp.consent.api.dto.sopd.UpdateSopdDocumentRequest;
import ru.t1debut.itcamp.consent.api.dto.sopd.UpdateSopdDocumentResponse;
import ru.t1debut.itcamp.consent.model.SopdDocument;

import java.io.IOException;
import java.util.List;

public interface SopdDocumentService {

    SopdDocument getPrimeSopdDocument();

    List<Integer> getAllSopdVersions();

    Resource getSopdDocumentResource(int version);

    Resource getSopdDocumentResource(SopdDocument sopdDocument) throws IOException;

    UpdateSopdDocumentResponse createSopdDocument(UpdateSopdDocumentRequest updateSopdDocumentRequest);

    SopdDocument findSopdDocument(int version);
}

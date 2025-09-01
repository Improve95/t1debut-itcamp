package ru.t1debut.itcamp.consent.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1debut.itcamp.consent.api.controller.spec.SopdControllerSpec;
import ru.t1debut.itcamp.consent.api.dto.sopd.UpdateSopdDocumentRequest;
import ru.t1debut.itcamp.consent.api.dto.sopd.UpdateSopdDocumentResponse;
import ru.t1debut.itcamp.consent.core.service.SopdDocumentService;

import java.util.List;

import static ru.t1debut.itcamp.consent.api.ApiPath.ALL;
import static ru.t1debut.itcamp.consent.api.ApiPath.NEW;
import static ru.t1debut.itcamp.consent.api.ApiPath.PRIME;
import static ru.t1debut.itcamp.consent.api.ApiPath.PRIME_PLACEHOLDER;
import static ru.t1debut.itcamp.consent.api.ApiPath.SOPDS;
import static ru.t1debut.itcamp.consent.api.ApiPath.VERSIONS;
import static ru.t1debut.itcamp.consent.api.ApiPath.VERSION_PLACEHOLDER;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SET_PRIME_DOC_PARAM_TAG;

@RequiredArgsConstructor
@RestController
@RequestMapping(SOPDS)
public class SopdController implements SopdControllerSpec {

    private final SopdDocumentService sopdDocumentService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping(VERSIONS + ALL)
    public ResponseEntity<List<Integer>> getSopdVersions() {
        var sopdVersionsResponse = sopdDocumentService.getAllSopdVersions();
        return ResponseEntity.ok(sopdVersionsResponse);
    }

    @GetMapping(VERSIONS + VERSION_PLACEHOLDER)
    public ResponseEntity<Resource> getSopdResource(@PathVariable @Valid @NotNull @Min(1) Integer version) {
        var sopdDocumentResource = sopdDocumentService.getSopdDocumentResource(version);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(sopdDocumentResource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(
            value = NEW + PRIME + PRIME_PLACEHOLDER,
            consumes = MediaType.TEXT_HTML_VALUE
    )
    public ResponseEntity<UpdateSopdDocumentResponse> createNewSopdDocument(
            @PathVariable @Valid @Parameter(description = SWAGGER_SET_PRIME_DOC_PARAM_TAG) boolean setPrime,
            @RequestBody @Valid String sopdDocumentHtmlBody
    ) {
        var updateSopdDocumentResponse = sopdDocumentService.createSopdDocument(
                UpdateSopdDocumentRequest.builder()
                        .sopdDocumentHtmlBody(sopdDocumentHtmlBody)
                        .setPrime(setPrime)
                        .build()
        );
        return ResponseEntity.ok(updateSopdDocumentResponse);
    }
}

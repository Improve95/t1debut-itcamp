package ru.t1debut.itcamp.consent.api.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseAlreadyExist;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseForbidden;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseIllegalDtoValue;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseIllegalValue;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseInternalServerError;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseNotFound;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseOk;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseUnauthorized;
import ru.t1debut.itcamp.consent.api.dto.email.UpdateEmailFormResponse;

import java.util.List;

import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_CREATE_EMAIL_FORM_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_GET_EMAIL_FORM_VERSIONS_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_GET_EMAIL_RESOURCE_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SET_PRIME_DOC_PARAM_TAG;

@ApiResponseOk
@ApiResponseInternalServerError
@ApiResponseIllegalDtoValue
@ApiResponseUnauthorized
public interface EmailControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    @Operation(summary = SWAGGER_GET_EMAIL_FORM_VERSIONS_TAG)
    ResponseEntity<List<Integer>> getSopdVersions();

    @ApiResponseIllegalDtoValue
    @ApiResponseNotFound
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    @Operation(summary = SWAGGER_GET_EMAIL_RESOURCE_TAG)
    ResponseEntity<Resource> getSopdResource(@PathVariable @Valid @NotNull @Min(1) Integer version);

    @ApiResponseForbidden
    @ApiResponseIllegalDtoValue
    @ApiResponseIllegalValue
    @ApiResponseAlreadyExist
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    @Operation(summary = SWAGGER_CREATE_EMAIL_FORM_TAG)
    ResponseEntity<UpdateEmailFormResponse> createNewEmailForm(
            @PathVariable @Valid @Parameter(description = SWAGGER_SET_PRIME_DOC_PARAM_TAG) boolean setPrime,
            @RequestBody @Valid String emailHtmlBody
    );
}

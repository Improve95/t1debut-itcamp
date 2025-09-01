package ru.t1debut.itcamp.consent.core.external.client.service;

public interface ExternalAuthClient {

    boolean checkAuthorizationRequest(String authorization);

    boolean emailExistRequest(String email, String authorization);
}

package ru.t1debut.itcamp.auth.service.core.service;

public interface PasswordService {

    String generateRandomPassword();

    String encodePassword(String password);
}

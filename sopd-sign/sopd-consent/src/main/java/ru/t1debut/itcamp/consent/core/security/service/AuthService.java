package ru.t1debut.itcamp.consent.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest request, HttpServletResponse response);
}

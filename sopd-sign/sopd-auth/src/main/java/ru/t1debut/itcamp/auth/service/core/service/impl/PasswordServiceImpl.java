package ru.t1debut.itcamp.auth.service.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.auth.service.core.service.PasswordService;

import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    private final Random random = new Random();

    @Override
    public String generateRandomPassword() {
        int passwordSize = random.nextInt(10, 15);
        byte[] password = new byte[passwordSize];
        for (int i = 0; i < passwordSize; i++) {
            password[i] = (byte) random.nextInt(97, 122);
        }
        return new String(password, UTF_8);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

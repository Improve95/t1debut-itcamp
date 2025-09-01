package ru.t1debut.itcamp.auth.service.core.security.service.impl;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.t1debut.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.CreateUserRequest;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.CreateUserResponse;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.SignInRequest;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.SignInResponse;
import ru.t1debut.itcamp.auth.service.api.exception.ServiceException;
import ru.t1debut.itcamp.auth.service.configuration.security.tokenConfig.TokenConfig;
import ru.t1debut.itcamp.auth.service.core.security.object.JwtToken;
import ru.t1debut.itcamp.auth.service.core.security.object.UserClaim;
import ru.t1debut.itcamp.auth.service.core.security.object.UserSessionDetails;
import ru.t1debut.itcamp.auth.service.core.security.service.AuthService;
import ru.t1debut.itcamp.auth.service.core.security.service.TokenCryptoService;
import ru.t1debut.itcamp.auth.service.core.service.EmailService;
import ru.t1debut.itcamp.auth.service.core.service.PasswordService;
import ru.t1debut.itcamp.auth.service.core.service.RoleService;
import ru.t1debut.itcamp.auth.service.core.service.TokenService;
import ru.t1debut.itcamp.auth.service.core.service.UserService;
import ru.t1debut.itcamp.auth.service.model.User;
import ru.t1debut.itcamp.auth.service.util.SecurityUtil;
import ru.t1debut.itcamp.auth.service.util.mapper.UserMapper;

import java.util.List;
import java.util.Set;

import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.EXPIRED;
import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.t1debut.itcamp.auth.service.util.SecurityUtil.AUTHORITIES_CLAIM;
import static ru.t1debut.itcamp.auth.service.util.SecurityUtil.USER_CLAIM;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

    private final UserService userService;

    private final RoleService roleService;

    private final UserMapper userMapper;

    private final TokenService accessTokenService;

    private final PasswordService passwordService;

    private final AuthenticationManager authManager;

    private final TokenCryptoService tokenCryptoService;

    private final TokenConfig tokenConfig;

    private final JWSSigner jwsSigner;

    private final EmailService emailService;

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken)) return true;

        try {
            JwtToken token = (JwtToken) auth.getPrincipal();
            int userId = Integer.parseInt(token.getSubject());

            if (tokenCryptoService.tokenIsExpired(token)) {
                throw new ServiceException(EXPIRED);
            }

            if (!accessTokenService.tokenIsPresent(token.getToken())) {
                throw new ServiceException(UNAUTHORIZED);
            }

            List<String> roles = token.getClaim(AUTHORITIES_CLAIM);
            UsernamePasswordAuthenticationToken userPasswordAuth =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .toList()
                    );

            UserClaim userClaim = SecurityUtil.getClaimObjectFromToken(token, USER_CLAIM, UserClaim.class);
            userPasswordAuth.setDetails(new UserSessionDetails(userClaim, token.getToken()));
            securityContext.setAuthentication(userPasswordAuth);

            return true;
        } catch (Exception ex) {
            securityContext.setAuthentication(null);
            SecurityContextHolder.clearContext();
            response.reset();
            if (ex instanceof ServiceException e && e.getCode() == EXPIRED) throw e;
            throw new ServiceException(UNAUTHORIZED);
        }
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        emailService.emailAddressIsExist(signInRequest.getEmail());
        User user = userMapper.toUser(signInRequest);
        user.setPassword(passwordService.encodePassword(signInRequest.getPassword()));
        user.setRoles(Set.of(roleService.getRole(SecurityUtil.MANAGER_ROLE)));
        user = userService.saveUser(user);
        return userMapper.toSignInUserResponse(user);
    }

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        emailService.emailAddressIsExist(createUserRequest.getEmail());
        User user = userMapper.toUser(createUserRequest);
        String password = passwordService.generateRandomPassword();
        user.setPassword(passwordService.encodePassword(password));
        user.setRoles(Set.of(roleService.getRole(SecurityUtil.MANAGER_ROLE)));
        User savedUser = userService.saveUser(user);
        return CreateUserResponse.builder()
                .id(savedUser.getId())
                .login(createUserRequest.getEmail())
                .password(password)
                .build();
    }

    @Transactional
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());

        Authentication authentication;
        try {
            authentication = authManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException ex) {
            throw new ServiceException(UNAUTHORIZED);
        }

        User user = (User) authentication.getPrincipal();
        if (user == null) throw new ServiceException(UNAUTHORIZED);

        JWTClaimsSet claims = SecurityUtil.createClaims(user, tokenConfig.getJws().duration());
        String accessToken = tokenCryptoService.createToken(claims, jwsSigner);
        accessTokenService.saveToken(accessToken, user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public void logout() {
        UserSessionDetails details = SecurityUtil.getSessionDetailsByAuth();
        accessTokenService.deleteToken(details.getToken());
    }

    @Override
    public void logoutAllToken() {
        int userId = SecurityUtil.getUserIdByAuth();
        accessTokenService.deleteAllPresentTokenByUserId(userId);
    }
}

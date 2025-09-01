package ru.t1debut.itcamp.auth.service.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.auth.service.api.dto.user.UserResponse;
import ru.t1debut.itcamp.auth.service.api.exception.ServiceException;
import ru.t1debut.itcamp.auth.service.core.repository.UserRepository;
import ru.t1debut.itcamp.auth.service.core.service.UserService;
import ru.t1debut.itcamp.auth.service.model.User;
import ru.t1debut.itcamp.auth.service.util.DatabaseUtil;
import ru.t1debut.itcamp.auth.service.util.SecurityUtil;
import ru.t1debut.itcamp.auth.service.util.mapper.UserMapper;

import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse getUserByAuth() {
        return getUser(SecurityUtil.getUserIdByAuth());
    }

    @Transactional
    @Override
    public UserResponse getUser(int id) {
        return userMapper.toUserResponse(findUser(id));
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (DatabaseUtil.isUniqueConstraintException(ex)) {
                throw new ServiceException(ALREADY_EXIST, "user", "email");
            }
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    @Override
    public User findUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "user", "id"));
    }
}

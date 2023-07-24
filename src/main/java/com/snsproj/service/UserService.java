package com.snsproj.service;

import com.snsproj.JwtTokenUtils;
import com.snsproj.exception.ErrorCode;
import com.snsproj.exception.SimpleSnsApplicationException;
import com.snsproj.model.User;
import com.snsproj.model.entity.UserEntity;
import com.snsproj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
                () -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
        );
    }

    public String login(String userName, String password) {
        User savedUser = loadUserByUsername(userName);
        if (!encoder.matches(password, savedUser.getPassword())) {
            throw new SimpleSnsApplicationException(ErrorCode.INVALID_PASSWORD, String.format("userName is %s", userName));
        }
        return JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
    }


    public User join(String userName, String password) {
        // check the userId not exist
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new SimpleSnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("userName is %s", userName));
        });

        UserEntity savedUser = userRepository.save(UserEntity.of(userName, encoder.encode(password)));
        return User.fromEntity(savedUser);
    }
}

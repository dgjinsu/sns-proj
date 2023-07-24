package com.snsproj.service;

import com.snsproj.exception.SnsApplicationException;
import com.snsproj.model.entity.UserEntity;
import com.snsproj.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {

        String userName = "userName";
        String password = "password";

        //mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @Test
    void 회원가입이_중복체크() {

        String userName = "userName";
        String password = "password";

        //mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password));
    }


    @Test
    void 로그인_정상동작() {

        String userName = "userName";
        String password = "password";

        //mocking
        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @Test
    void 로그인시_아이디_없음() {

        String userName = "userName";
        String password = "password";

        //mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
    }

    @Test
    void 로그인_비밀번호_틀림() {

        String userName = "userName";
        String password = "password";

        //mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
    }
}
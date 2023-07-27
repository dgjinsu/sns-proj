package com.snsproj.controller;

import com.snsproj.controller.request.UserJoinRequest;
import com.snsproj.controller.request.UserLoginRequest;
import com.snsproj.controller.response.AlarmResponse;
import com.snsproj.controller.response.Response;
import com.snsproj.controller.response.UserJoinResponse;
import com.snsproj.controller.response.UserLoginResponse;
import com.snsproj.exception.ErrorCode;
import com.snsproj.exception.SimpleSnsApplicationException;
import com.snsproj.model.User;
import com.snsproj.service.UserService;
import com.snsproj.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        return Response.success(UserJoinResponse.fromUser(userService.join(request.getName(), request.getPassword())));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/me")
    public Response<UserJoinResponse> me(Authentication authentication) {
        return Response.success(UserJoinResponse.fromUser(userService.loadUserByUsername(authentication.getName())));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        User user = ClassUtils.getSafeInstance(authentication.getPrincipal(), User.class)
                .orElseThrow(() -> new SimpleSnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to User class failed"));
        Page<AlarmResponse> result = userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm);
        return Response.success(result);
    }
}

package com.snsproj.controller.response;

import com.snsproj.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private Integer id;
    private String name;

    public static UserJoinResponse fromUser(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getUsername()
        );
    }
}

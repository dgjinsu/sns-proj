package com.snsproj.controller.response;

import com.snsproj.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class UserResponse {
    private Integer id;
    private String name;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername()
        );
    }

}
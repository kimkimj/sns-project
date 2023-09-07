package com.example.finalproject.domain.dto.user;

import com.example.finalproject.domain.entity.User;
import com.example.finalproject.domain.UserRole;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private String username;
    private String password;

    public User toEntity(String password) {
        return new User(username, password, UserRole.USER);
    }
}


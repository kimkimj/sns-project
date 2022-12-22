package com.example.finalproject.domain.dto;

import com.example.finalproject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJoinRequest {
    private String userName;
    private String password;

    public User toEntity(String password) {
        return User.builder()
                .username(this.userName)
                .password(password)
                .build();
    }

}

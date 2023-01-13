package com.example.finalproject.domain.dto.user;

import com.example.finalproject.domain.entity.User;
import com.example.finalproject.domain.UserRole;
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
                .role(UserRole.USER) // join 요청 시, 'USER' 가 default Role로 입력 됨
                .build();
    }

}


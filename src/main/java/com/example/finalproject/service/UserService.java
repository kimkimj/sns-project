package com.example.finalproject.service;

import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.UserJoinRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDto join(UserJoinRequest request) {
        return new UserDto("", "");
    }
}

package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.UserJoinRequest;
import com.example.finalproject.domain.dto.UserJoinResponse;
import com.example.finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto userDto = userService.join(userJoinRequest);
        return Response.success(new UserJoinResponse(userDto.getUsername()));
    }
}

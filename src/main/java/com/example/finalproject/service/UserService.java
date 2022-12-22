package com.example.finalproject.service;

import com.example.finalproject.domain.User;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.UserJoinRequest;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    // 중복 체크를 위해 DB에 다녀와야 한다
    private final UserRepository userRepository;
    //private final BCryptPasswordEncoder encode;

    public UserDto  join(UserJoinRequest request) {

        // username 중복 체크해서 이미 존재하는 아이디면 exception
        userRepository.findByUsername(request.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED,
                            String.format("%s은 이미 존재하는 아이디 입니다", request.getUserName()));
                });

        // 아니라면 저장
        User savedUser = userRepository.save(request.toEntity());

        // 반환
        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .build();
    }
}

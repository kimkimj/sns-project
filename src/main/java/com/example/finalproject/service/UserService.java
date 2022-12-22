package com.example.finalproject.service;

import com.example.finalproject.domain.User;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.UserJoinRequest;
import com.example.finalproject.domain.dto.UserLoginRequest;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.UserRepository;
import com.example.finalproject.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    // 중복 체크를 위해 DB에 다녀와야 한다
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60l; // 1시간 후 expire 한다

    public UserDto join(UserJoinRequest request) {

        // username 중복 체크해서 이미 존재하는 아이디면 exception
        userRepository.findByUsername(request.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED,
                            String.format("%s은 이미 존재하는 아이디 입니다", request.getUserName()));
                });

        // 아니라면 저장
        User savedUser = userRepository.save(request.toEntity(encoder.encode(request.getPassword())));

        // 반환
        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .build();
    }

    public String login(UserLoginRequest request) {

        // username이 db에 없을 때
        User selectedUser = userRepository.findByUsername(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND,
                        String.format("%s 이 없습니다", request.getUserName())));

        // wrong password
        if(!encoder.matches(request.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호를 잘못 입력했습니다");
        }

        String token = JwtTokenUtil.createToken(selectedUser.getUsername(), key, expireTimeMs);

        return token;
    }
}

package com.example.finalproject.service;

import com.example.finalproject.domain.dto.user.UserJoinResponse;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.domain.dto.user.UserJoinRequest;
import com.example.finalproject.domain.dto.user.UserLoginRequest;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.UserRepository;
import com.example.finalproject.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    // 중복 체크를 위해 DB에서 확인
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60l; // 1시간 후 expire 한다

    public UserJoinResponse join(UserJoinRequest request) {

        // username 중복 체크해서 이미 존재하는 아이디면 exception
        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED,
                            String.format("%s은 이미 존재하는 아이디 입니다", request.getUsername()));
                });

        // 아니라면 저장
        User savedUser = userRepository.save(request.toEntity(encoder.encode(request.getPassword())));

        return new UserJoinResponse(savedUser.getUsername());
    }

    public String login(UserLoginRequest request) {

        // username이 db에 없을 때
        User selectedUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND,
                        String.format("%s 이 없습니다", request.getUsername())));

        // wrong password
        if(!encoder.matches(request.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호를 잘못 입력했습니다");
        }

        String token = JwtTokenUtil.createToken(selectedUser.getUsername(), key, expireTimeMs);

        return token;
    }

    // userName으로 User 검색. 없으면 USERNAME_NOT_FOUND 발생 시킴
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));
    }
}



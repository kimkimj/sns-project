package com.example.finalproject.service;

import com.example.finalproject.domain.User;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    // 중복 체크를 위해 DB에 다녀와야 한다
    private final UserRepository userRepository;
    //private final BCryptPasswordEncoder encode;

    public String join(String username, String password) {

        // username 중복 체크해서 이미 존재하는 아이디면 exception
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 아이디 입니다");
                });

        // 아니라면 저장
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        userRepository.save(user);

        // 반환
        return "SUCCESS";
    }
}

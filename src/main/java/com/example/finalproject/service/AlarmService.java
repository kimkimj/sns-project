package com.example.finalproject.service;

import com.example.finalproject.domain.dto.alarm.AlarmListResponse;
import com.example.finalproject.domain.dto.alarm.AlarmResponse;
import com.example.finalproject.domain.entity.Alarm;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.AlarmRepository;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public AlarmListResponse getAlarms(String username) {

        // 존재하는 유저인지 확인

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // Sort.by의 properties는 entity의 field name으로 해야한다
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "registeredAt"));

        Page<Alarm> alarms = alarmRepository.findAllByTargetUserId(user.getUserId(), pageable);
        return new AlarmListResponse(alarms);
    }
}

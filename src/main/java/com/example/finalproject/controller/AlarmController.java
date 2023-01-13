package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.alarm.AlarmListResponse;
import com.example.finalproject.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public Response<AlarmListResponse> getAlarms(@ApiIgnore Authentication authentication, @ApiIgnore Pageable pageable) {
        AlarmListResponse alarms = alarmService.getAlarms(authentication.getName());
        return Response.success(alarms);
    }
}


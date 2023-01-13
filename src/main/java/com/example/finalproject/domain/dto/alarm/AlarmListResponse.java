package com.example.finalproject.domain.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AlarmListResponse {
    private List<AlarmResponse> content;
    private Pageable pageable;
}


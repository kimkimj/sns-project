package com.example.finalproject.domain.dto.alarm;

import com.example.finalproject.domain.entity.Alarm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AlarmListResponse {
    private List<AlarmResponse> content;

    public AlarmListResponse(Page<Alarm> alarms) {
        content = alarms.stream().map(
                alarm -> new AlarmResponse(
                        alarm.getAlarmType(),
                        alarm.getFromUserId(),
                        alarm.getTargetUserId(),
                        alarm.getText(),
                        alarm.getRegisteredAt()
                ))
                .collect(Collectors.toList());
    }
}


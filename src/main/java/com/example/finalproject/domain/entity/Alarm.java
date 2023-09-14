package com.example.finalproject.domain.entity;

import com.example.finalproject.domain.dto.alarm.AlarmType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;
    private String text;

    @CreatedDate
    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long targetUserId;

    private Long fromUserId;

    public Alarm(AlarmType alarmType, String text, LocalDateTime registeredAt, User user, Long targetUserId, Long fromUserId) {
        this.alarmType = alarmType;
        this.text = text;
        this.registeredAt = registeredAt;
        this.user = user;
        this.targetUserId = targetUserId;
        this.fromUserId = fromUserId;
    }
}


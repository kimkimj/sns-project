package com.example.finalproject.respository;

import com.example.finalproject.domain.entity.Alarm;
import com.example.finalproject.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    public Page<Alarm> findAllByUser(User user, Pageable pageable);
}


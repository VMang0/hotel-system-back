package com.example.hotel.repositories;

import com.example.hotel.models.CallStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallStatusRepository extends JpaRepository<CallStatus, Long> {
    CallStatus findByName(String status);
}


package com.example.hotel.repositories;

import com.example.hotel.models.Status;
import com.example.hotel.models.Type_room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(String status);
}

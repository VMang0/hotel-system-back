package com.example.hotel.repositories;

import com.example.hotel.models.Calls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallsRepository extends JpaRepository<Calls, Long> {
}

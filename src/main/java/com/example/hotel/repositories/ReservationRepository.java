package com.example.hotel.repositories;

import com.example.hotel.models.Reservation;
import com.example.hotel.models.Room;
import com.example.hotel.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByRoomAndStatusIn(Room room, List<Status> status);
}

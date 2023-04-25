package com.example.hotel.repositories;

import com.example.hotel.models.Reservation;
import com.example.hotel.models.Room;
import com.example.hotel.models.Status;
import com.example.hotel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByRoomAndStatusIn(Room room, List<Status> status);
    List<Reservation> findAllByUser(Optional<User> user);
    List<Reservation> findAllByStatus(Status status);
}

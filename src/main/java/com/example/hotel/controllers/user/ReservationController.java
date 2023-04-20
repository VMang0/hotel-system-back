package com.example.hotel.controllers.user;

import com.example.hotel.DTO.ReservationDTO;
import com.example.hotel.models.Reservation;
import com.example.hotel.models.Room;
import com.example.hotel.models.Status;
import com.example.hotel.repositories.ReservationRepository;
import com.example.hotel.repositories.RoomRepository;
import com.example.hotel.repositories.StatusRepository;
import com.example.hotel.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private StatusRepository statusRepository;

    @PostMapping("/add_reservation")
    public ResponseEntity<Reservation> newReservation(@RequestBody ReservationDTO reservationDTO) throws IOException {
        Reservation createReservation = reservationService.createNewReserv(reservationDTO);
        return new ResponseEntity<>(createReservation, HttpStatus.CREATED);
    }

    @GetMapping("/reservations/dates/{id}")
    public ResponseEntity<List<LocalDate>> getReservationDatesByRoomId(@PathVariable Long id) {
        List<LocalDate> dates = reservationService.getReservationDates(id);
        if (dates == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dates);
    }


}

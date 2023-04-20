package com.example.hotel.services;

import com.example.hotel.DTO.ReservationDTO;
import com.example.hotel.DTO.RoomDTO;
import com.example.hotel.models.*;
import com.example.hotel.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TypeMealsRepository typeMealsRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    public Reservation createNewReserv(ReservationDTO reservationDTO) throws IOException {
        Reservation reservation = new Reservation();
        Status status = statusRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Invalid status id"));
        reservation.setStatus(status);

        long userId = Long.parseLong(String.valueOf(reservationDTO.getUser()));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        reservation.setUser(user);
        Type_meal type_meal = typeMealsRepository.findByName(reservationDTO.getType_meal());
        reservation.setType_meal(type_meal);
        Room room = roomRepository.findById((long) reservationDTO.getRoom()).orElseThrow(() -> new IllegalArgumentException("Invalid room id"));
        reservation.setRoom(room);

        reservation.setName(reservationDTO.getName());
        reservation.setLastname(reservationDTO.getLastname());
        reservation.setPatronymic(reservationDTO.getPatronymic());
        reservation.setPhoneNumber(reservationDTO.getPhoneNumber());
        reservation.setBirthDate(reservationDTO.getBirthDate());
        reservation.setPayment(reservationDTO.getPayment());
        reservation.setStartdate(reservationDTO.getStartdate());
        reservation.setEnddate(reservationDTO.getEnddate());
        reservation.setReservdate(reservationDTO.getReservdate());
        reservation.setNumAdult(reservationDTO.getNumAdult());
        reservation.setNumChild(reservationDTO.getNumChild());
        reservation.setCost(reservationDTO.getCost());
        return reservationRepository.save(reservation);
    }

    public List<LocalDate> getReservationDates(Long id){
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return null;
        }
        List<Status> status = statusRepository.findAllById(Arrays.asList(1L, 2L));
        List<Reservation> reservations = reservationRepository.findAllByRoomAndStatusIn(room.get(), status);
        List<LocalDate> dates = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dates.addAll(getDatesBetween(reservation.getStartdate(), reservation.getEnddate()));
        }
        return dates;
    }

    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
    }



}

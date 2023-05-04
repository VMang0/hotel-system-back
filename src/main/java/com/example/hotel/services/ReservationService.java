package com.example.hotel.services;

import com.example.hotel.DTO.ReservationDTO;
import com.example.hotel.DTO.ReservationUserDTO;
import com.example.hotel.models.*;
import com.example.hotel.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TypeMealsRepository typeMealsRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final CardRepository cardRepository;
    @Autowired
    private JavaMailSender mailSender;

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

    public ResponseEntity<?> update(Long id, String status){
        Status status1 = statusRepository.findByName(status);
        reservationRepository.findById(id).map(reservation -> {
            reservation.setStatus(status1);
            return reservationRepository.save(reservation);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id){
        if (!reservationRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservationRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Reservation getReservationById(Long id){
        return reservationRepository.getById(id);
    }

    public List<LocalDate> getReservationDates(Long id){
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return null;
        }
        List<Status> status = statusRepository.findAllById(Arrays.asList(1L, 2L));
        List<Reservation> reservations = reservationRepository.findAllByRoomAndStatusIn(room, status);
        List<LocalDate> dates = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dates.addAll(getDatesBetween(reservation.getStartdate(), reservation.getEnddate()));
        }

        List<LocalDate> todayDates = dates.stream()
                .filter(date -> date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        return todayDates;
    }

    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
    }

    public List<ReservationUserDTO> getReservationByUser(Long id){
        Optional<User> user = userRepository.findById(id);
        List<Reservation> reservationList = reservationRepository.findAllByUser(user);
        List<ReservationUserDTO> reservationUserDTOList = new ArrayList<>();

        for(Reservation reservation: reservationList){
            ReservationUserDTO reservationUserDTO = new ReservationUserDTO();
            reservationUserDTO.setId(reservation.getId());
            Room room = roomRepository.getById(reservation.getRoom().getId());
            reservationUserDTO.setNameRoom(room.getName());
            reservationUserDTO.setStartdate(reservation.getStartdate());
            reservationUserDTO.setEnddate(reservation.getEnddate());
            reservationUserDTO.setNumAdult(reservation.getNumAdult());
            reservationUserDTO.setNumChild(reservation.getNumChild());
            Type_meal type_meal = typeMealsRepository.getById(reservation.getType_meal().getId());
            reservationUserDTO.setType_meal(type_meal.getName());
            reservationUserDTO.setCost(reservation.getCost());
            reservationUserDTO.setPayment(reservation.getPayment());
            reservationUserDTO.setReservdate(reservation.getReservdate());
            Status status = statusRepository.getById(reservation.getStatus().getId_status());
            reservationUserDTO.setStatus(status.getName());
            reservationUserDTOList.add(reservationUserDTO);
        }
        return reservationUserDTOList;
    }

    public void getReservationsUpdateStatusToCancel(Long id){
        Reservation reservation = reservationRepository.getById(id);
        Status status = statusRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("Invalid status id"));
        reservation.setStatus(status);
        reservationRepository.save(reservation);
    }

    public void getPayReservation(Long id, Card card){
        Reservation reservation = reservationRepository.getById(id);
        Status status = statusRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException("Invalid status id"));
        reservation.setStatus(status);
        cardRepository.save(card);
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void cancelExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        Status status = statusRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Invalid status id"));
        List<Reservation> reservations = reservationRepository.findAllByStatus(status);
        for (Reservation reservation : reservations) {
            LocalDateTime reservDate = reservation.getReservdate();
            Duration duration = Duration.between(reservDate, now);
            if (duration.toHours() >= 24) {
                reservation.setStatus(statusRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("Invalid status id")));
                reservationRepository.save(reservation);
                sendEmail(reservation.getUser().getEmail());
            }
        }
    }

    public void sendEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mangwork960@gmail.com");
        message.setTo(email);
        message.setSubject("VMANG0");
        message.setText("Ваша бронь в отеле VMang0 аннулирована в связи с неоплатой! С уважением, администрация отеля VMang0!");
        mailSender.send(message);
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void cancelConfirmedReservations() {
        LocalDate now = LocalDate.now();
        Status status = statusRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException("Invalid status id"));
        List<Reservation> reservations = reservationRepository.findAllByStatus(status);
        for (Reservation reservation : reservations) {
            LocalDate endDate = reservation.getEnddate();
            long daysBetween = ChronoUnit.DAYS.between(endDate, now);
            if (daysBetween >= 1) {
                reservation.setStatus(statusRepository.findById(4L).orElseThrow(() -> new IllegalArgumentException("Invalid status id")));
                reservationRepository.save(reservation);
            }
        }
    }

    public  List<Reservation> getResevationByStatus(){
        List<Status> status = statusRepository.findAllById(Arrays.asList(1L, 2L));
        return reservationRepository.findAllByStatusIn(status);
    }
}

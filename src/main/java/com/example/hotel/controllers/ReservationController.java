package com.example.hotel.controllers;

import com.example.hotel.DTO.ReservationDTO;
import com.example.hotel.DTO.ReservationUserDTO;
import com.example.hotel.configuratoins.Doc;
import com.example.hotel.models.*;
import com.example.hotel.repositories.ReservationRepository;
import com.example.hotel.repositories.StatusRepository;
import com.example.hotel.repositories.UserRepository;
import com.example.hotel.services.ReservationService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;


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

    @GetMapping("/list/reservations")
    public List<Reservation> getReservationsList(){return reservationRepository.findAll();}

    @GetMapping("/list/status")
    List<Status> getAllStatus() { return statusRepository.findAll(); }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        return reservationService.delete(id);
    }

    @PutMapping("/reservation/{id}/{status}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @PathVariable String status) {
        return reservationService.update(id, status);
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<?> createContract(@PathVariable Long id) throws IOException, DocumentException {
        Reservation reservation = reservationService.getReservationById(id);
        Doc doc = new Doc();
        return doc.CreatePDF(reservation);
    }

    @GetMapping("/reservations/user/{id}")
    public ResponseEntity<List<ReservationUserDTO>> getReservationByUser(@PathVariable Long id) {
        List<ReservationUserDTO> reservationList = reservationService.getReservationByUser(id);
        if (reservationList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationList);
    }

    @PutMapping("/personalreserv/{id}")
    public ResponseEntity<?> updateReservationStatusToCancel(@PathVariable Long id){
        reservationService.getReservationsUpdateStatusToCancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/personalreserv/{id}")
    public ResponseEntity<?> payReservation(@PathVariable Long id, @RequestBody Card card){
        reservationService.getPayReservation(id, card);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public List<Reservation> verify(@PathVariable Long id) {
        List<Status> status = statusRepository.findAllById(Arrays.asList(1L, 2L));
        return reservationRepository.findAllByUserAndStatusIn(userRepository.findById(id), status);
    }

    @GetMapping("/reservation/by/status")
    public ResponseEntity<List<Reservation>> getReservayionByStatus() {
       List<Reservation> reservations = reservationService.getResevationByStatus();
        return ResponseEntity.ok(reservations);
    }

}

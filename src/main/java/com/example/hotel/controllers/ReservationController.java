package com.example.hotel.controllers;

import com.example.hotel.DTO.ReservationDTO;
import com.example.hotel.DTO.ReservationUserDTO;
import com.example.hotel.configuratoins.NotFoundException;
import com.example.hotel.models.*;
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
import java.util.*;

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

  /*  @PostMapping("/contracts/{id}")
    public ResponseEntity<?> createContract(@PathVariable Long id, @RequestBody Contract request) throws IOException, DocumentException {
        String folderPath = request.getFolderPath();
        Reservation reservation = reservationRepository.getById(id);
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Договор на бронирование"));
        document.add(new Paragraph("Информация о бронировании:"));
        document.add(new Paragraph("Имя: " + reservation.getName()));
        document.add(new Paragraph("Фамилия: " + reservation.getLastname()));
        document.add(new Paragraph("Отчество: " + reservation.getPatronymic()));
        document.add(new Paragraph("Номер телефона: " + reservation.getPhoneNumber()));
        document.close();
        String fileName = "contract.pdf";
        System.out.println("ПУТЬ" + folderPath);
        Path path = Paths.get(folderPath + fileName);

        Files.write(path, baos.toByteArray());

        return ResponseEntity.ok().build();
    }*/

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

}

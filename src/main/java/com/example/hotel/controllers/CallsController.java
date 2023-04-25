package com.example.hotel.controllers;

import com.example.hotel.configuratoins.NotFoundException;
import com.example.hotel.models.CallStatus;
import com.example.hotel.models.Calls;
import com.example.hotel.models.Reservation;
import com.example.hotel.repositories.CallStatusRepository;
import com.example.hotel.repositories.CallsRepository;
import com.example.hotel.services.CallsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class CallsController {

    @Autowired
    private CallStatusRepository callStatusRepository;
    @Autowired
    private CallsRepository callsRepository;
    @Autowired
    private CallsService callsService;

    @PostMapping("/call_request")
    public ResponseEntity<?> newCallRequest(@RequestBody Calls calls) throws IOException {
       return callsService.addCall(calls);
    }

    @GetMapping("/call_request/list")
    List<Calls> getCallRequest(){return callsRepository.findAll();}

    @PutMapping("/call_request/{id}/{status}")
    public ResponseEntity<?> updateCallRequestStatus(@PathVariable Long id, @PathVariable String status) {
        return callsService.update(id, status);
    }

    @GetMapping("/call_request/status/list")
    List<CallStatus> getCallStatus(){return callStatusRepository.findAll();}

    @DeleteMapping("/call_request/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return callsService.delete(id);
    }

}

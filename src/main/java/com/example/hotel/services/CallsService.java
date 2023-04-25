package com.example.hotel.services;

import com.example.hotel.models.CallStatus;
import com.example.hotel.models.Calls;
import com.example.hotel.models.Status;
import com.example.hotel.repositories.CallStatusRepository;
import com.example.hotel.repositories.CallsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class CallsService {
    private final CallsRepository callsRepository;
    private final CallStatusRepository callStatusRepository;

    public ResponseEntity<?> addCall(Calls calls){
        CallStatus callStatus = callStatusRepository.getById(1L);
        calls.setCallStatus(callStatus);
        callsRepository.save(calls);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long id, String status){
        CallStatus status1 = callStatusRepository.findByName(status);
        callsRepository.findById(id).map(calls -> {
            calls.setCallStatus(status1);
            return callsRepository.save(calls);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id){
        if (!callsRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        callsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.example.hotel.services;

import com.example.hotel.DTO.RoomDTO;
import com.example.hotel.models.*;
import com.example.hotel.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final TypeRoomRepository typeRoomRepository;
    private final TypeBedRepository typeBedRepository;
    private final TypeMealsRepository typeMealsRepository;
    private final ReservationRepository reservationRepository;
    private final StatusRepository statusRepository;

    public Room createNewRoom(RoomDTO roomDTO, MultipartFile[] files) throws IOException {
        Room room = new Room();

        room.setNumber(roomDTO.getNumber());
        room.setCost(roomDTO.getCost());
        room.setName(roomDTO.getName());
        room.setSquare(roomDTO.getSquare());
        room.setDescription(roomDTO.getDescription());

        Type_room type_room = typeRoomRepository.findByName(roomDTO.getType_room());
        room.setType_room(type_room);
        Type_bed type_bed = typeBedRepository.findByName(roomDTO.getType_bed());
        room.setType_bed(type_bed);

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i] != null && !files[i].isEmpty()) {
                    Image image = toImageEntity(files[i]);
                    room.addImageToProduct(image);
                }
            }
        }

        return roomRepository.save(room);
    }
    public ResponseEntity<?> delete(Long id){
        List<Status> status = statusRepository.findAllById(Arrays.asList(1L, 2L));
        List<Reservation> reservations = reservationRepository.findAllByRoomAndStatusIn(roomRepository.findById(id), status);
        if (!roomRepository.existsById(id) || !reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        roomRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setBytes(file.getBytes());
        return image;
    }

    public List<RoomDTO> findAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        List<RoomDTO> rooms1 = new ArrayList<>();
        for(Room room: rooms){
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(room.getId());
            roomDTO.setNumber(room.getNumber());
            roomDTO.setCost(room.getCost());
            roomDTO.setName(room.getName());
            roomDTO.setSquare(room.getSquare());
            roomDTO.setDescription(room.getDescription());
            Type_room type_room = typeRoomRepository.getById(room.getType_room().getId());
            roomDTO.setType_room(type_room.getName());
            Type_bed type_bed = typeBedRepository.getById(room.getType_bed().getId());
            roomDTO.setType_bed(type_bed.getName());
            rooms1.add(roomDTO);
        }
        return rooms1;
    }

    public RoomDTO findOneRoom(Long id){
        RoomDTO roomDTO = new RoomDTO();
        Optional<Room> room = roomRepository.findById(id);
        roomDTO.setId(room.get().getId());
        roomDTO.setCost(room.get().getCost());
        roomDTO.setNumber(room.get().getNumber());
        roomDTO.setSquare(room.get().getSquare());
        roomDTO.setName(room.get().getName());
        roomDTO.setDescription(room.get().getDescription());

        Type_room type_room = typeRoomRepository.getById(room.get().getType_room().getId());
        roomDTO.setType_room(type_room.getName());
        Type_bed type_bed = typeBedRepository.getById(room.get().getType_bed().getId());
        roomDTO.setType_bed(type_bed.getName());
        return roomDTO;
    }

    public ResponseEntity<?> updateMeals(Long id, double cost){
        typeMealsRepository.findById(id).map(meals -> {
            meals.setCost(cost);
            return typeMealsRepository.save(meals);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> updateRoom(RoomDTO roomDTO){
        Type_room type_room = typeRoomRepository.findByName(roomDTO.getType_room());
        Type_bed type_bed = typeBedRepository.findByName(roomDTO.getType_bed());
        Room room = roomRepository.findById(roomDTO.getId()).map(room1 -> {
            room1.setNumber(roomDTO.getNumber());
            room1.setCost(roomDTO.getCost());
            room1.setDescription(roomDTO.getDescription());
            room1.setSquare(roomDTO.getSquare());
            room1.setName(roomDTO.getName());
            room1.setType_room(type_room);
            room1.setType_bed(type_bed);
            return roomRepository.save(room1);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

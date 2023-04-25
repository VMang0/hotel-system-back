package com.example.hotel.controllers;


import com.example.hotel.DTO.RoomDTO;
import com.example.hotel.configuratoins.NotFoundException;
import com.example.hotel.models.*;
import com.example.hotel.repositories.RoomRepository;
import com.example.hotel.repositories.TypeBedRepository;
import com.example.hotel.repositories.TypeMealsRepository;
import com.example.hotel.repositories.TypeRoomRepository;
import com.example.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private TypeRoomRepository typeRoomRepository;
    @Autowired
    private TypeBedRepository typeBedRepository;
    @Autowired
    private TypeMealsRepository typeMealsRepository;

    @PostMapping("/add_room")
    public ResponseEntity<Room> newRoom( @RequestParam("room") RoomDTO roomDTO, @RequestParam("files") MultipartFile[] files) throws IOException {
        Room createroom = roomService.createNewRoom(roomDTO, files);
        return new ResponseEntity<>(createroom, HttpStatus.CREATED);
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        return roomService.delete(id);
    }

    @GetMapping("/list/rooms")
    List<RoomDTO> getAllRooms(){return roomService.findAllRooms();}

    @GetMapping("/room/{id}")
    RoomDTO getRoomById(@PathVariable Long id) {
        return roomService.findOneRoom(id);
    }

    @GetMapping("/list/type_meals")
    List<Type_meal> getListType_Meals(){ return typeMealsRepository.findAll();}

    @GetMapping("/list/type_room")
    List<Type_room> getAllTypeRooms() {
        return typeRoomRepository.findAll();
    }

    @GetMapping("/list/type_bed")
    List<Type_bed> getAllTypeBeds() {
        return typeBedRepository.findAll();
    }

}

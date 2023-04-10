package com.example.hotel.repositories;

import com.example.hotel.models.Type_room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeRoomRepository extends JpaRepository<Type_room, Long> {
    Type_room findByName(String type_room);

    Type_room getById(Long type_room);
}

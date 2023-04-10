package com.example.hotel.repositories;

import com.example.hotel.models.Image;
import com.example.hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByRoom_Id(Long id);
}

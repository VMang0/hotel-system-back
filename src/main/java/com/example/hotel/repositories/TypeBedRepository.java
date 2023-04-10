package com.example.hotel.repositories;

import com.example.hotel.models.Type_bed;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeBedRepository extends JpaRepository<Type_bed, Long> {
    Type_bed findByName(String type_bed);

    Type_bed getById(Long type_bed);
}

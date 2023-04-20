package com.example.hotel.repositories;

import com.example.hotel.models.Type_bed;
import com.example.hotel.models.Type_meal;
import com.example.hotel.models.Type_room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeMealsRepository extends JpaRepository<Type_meal, Long> {
    Type_meal getById(Long type_meal);
    Type_meal findByName(String type_meal);
}

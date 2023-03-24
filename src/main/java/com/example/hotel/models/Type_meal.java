package com.example.hotel.models;

import javax.persistence.*;

@Entity
@Table(name = "type_meal")
public class Type_meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_meal")
    private Long id_type_meal;
    @Column(name = "name_type_meal")
    private String name_type_meal;

    public Long getId_type_meal() {
        return id_type_meal;
    }

    public void setId_type_meal(Long id_type_meal) {
        this.id_type_meal = id_type_meal;
    }

    public String getName_type_meal() {
        return name_type_meal;
    }

    public void setName_type_meal(String name_type_meal) {
        this.name_type_meal = name_type_meal;
    }
}

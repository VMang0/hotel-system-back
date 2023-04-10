package com.example.hotel.models;

import javax.persistence.*;

@Entity
@Table(name = "type_bed")
public class Type_bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_bed")
    private Long id;
    @Column(name = "name_type_bed")
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
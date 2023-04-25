package com.example.hotel.models;

import javax.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long id_status;
    @Column(name = "type_status")
    private String name;

    public Long getId_status() {
        return id_status;
    }

    public void setId_status(Long id_status) {
        this.id_status = id_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

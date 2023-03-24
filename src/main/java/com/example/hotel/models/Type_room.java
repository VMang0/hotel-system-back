package com.example.hotel.models;

import javax.persistence.*;

@Entity
@Table(name = "type_room")
public class Type_room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_room")
    private Long id_type_room;
    @Column(name = "name_type_room")
    private String name_type_room;

    public Long getId_type_room() {
        return id_type_room;
    }

    public void setId_type_room(Long id_type_room) {
        this.id_type_room = id_type_room;
    }

    public String getName_type_room() {
        return name_type_room;
    }

    public void setName_type_room(String name_type_room) {
        this.name_type_room = name_type_room;
    }
}

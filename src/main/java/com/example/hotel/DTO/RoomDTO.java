package com.example.hotel.DTO;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public class RoomDTO {
    private Long id;
    private String type_room;
    private String type_bed;
    private int number;
    private double cost;
    private double square;
    private String description;
    private String name;

    public RoomDTO(){

    }
    public RoomDTO(String type_room, String type_bed, int number, double cost, double square, String description, String name) {
        this.type_room = type_room;
        this.type_bed = type_bed;
        this.number = number;
        this.cost = cost;
        this.square = square;
        this.description = description;
        this.name = name;
    }

    public String getType_room() {
        return type_room;
    }

    public void setType_room(String type_room) {
        this.type_room = type_room;
    }

    public String getType_bed() {
        return type_bed;
    }

    public void setType_bed(String type_bed) {
        this.type_bed = type_bed;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

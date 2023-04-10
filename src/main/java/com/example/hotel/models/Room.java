package com.example.hotel.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_room")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_room")
    private Type_room type_room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_bed")
    private Type_bed type_bed;

    private int number;
    private double cost;
    private double square;
    private String description;
    private String name;

    ////////////
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "room")
    private List<Image> images = new ArrayList<>();
    /////////////

    public Type_room getType_room() {
        return type_room;
    }

    public void setType_room(Type_room type_room) {
        this.type_room = type_room;
    }

    public Type_bed getType_bed() {
        return type_bed;
    }

    public void setType_bed(Type_bed type_bed) {
        this.type_bed = type_bed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public  void addImageToProduct(Image image){
        image.setRoom(this);
        images.add(image);
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

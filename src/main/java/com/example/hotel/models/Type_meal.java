package com.example.hotel.models;

import javax.persistence.*;

@Entity
@Table(name = "type_meal")
public class Type_meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_meal")
    private Long id;
    @Column(name = "name_type_meal")
    private String name;

    @Column(name = "cost_meal")
    private double cost;


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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

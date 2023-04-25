package com.example.hotel.DTO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationUserDTO {
    private Long id;
    private String nameRoom;
    private LocalDate startdate;
    private LocalDate enddate;
    private int numAdult;
    private int numChild;
    private String type_meal;
    private double cost;
    private String payment;
    private LocalDateTime reservdate;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public int getNumAdult() {
        return numAdult;
    }

    public void setNumAdult(int numAdult) {
        this.numAdult = numAdult;
    }

    public int getNumChild() {
        return numChild;
    }

    public void setNumChild(int numChild) {
        this.numChild = numChild;
    }

    public String getType_meal() {
        return type_meal;
    }

    public void setType_meal(String type_meal) {
        this.type_meal = type_meal;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public LocalDateTime getReservdate() {
        return reservdate;
    }

    public void setReservdate(LocalDateTime reservdate) {
        this.reservdate = reservdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

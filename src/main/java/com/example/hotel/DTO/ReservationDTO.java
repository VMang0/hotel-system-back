package com.example.hotel.DTO;

import com.example.hotel.models.Room;
import com.example.hotel.models.Status;
import com.example.hotel.models.Type_meal;
import com.example.hotel.models.User;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

public class ReservationDTO {
    private Long id;
    private int user;
    private String name;
    private String lastname;
    private String patronymic;
    private String phoneNumber;
    private Date birthDate;
    private String payment;
    private LocalDate startdate;
    private LocalDate enddate;
    private Date reservdate;
    private String type_meal;
    private int numAdult;
    private int numChild;
    private int room;
    private double cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }


    public Date getReservdate() {
        return reservdate;
    }

    public void setReservdate(Date reservdate) {
        this.reservdate = reservdate;
    }

    public String getType_meal() {
        return type_meal;
    }

    public void setType_meal(String type_meal) {
        this.type_meal = type_meal;
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

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
}

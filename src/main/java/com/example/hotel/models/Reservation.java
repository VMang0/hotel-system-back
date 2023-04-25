package com.example.hotel.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "name_user")
    private String name;

    @Column(name = "lastname_user")
    private String lastname;

    @Column(name = "patronymic_user")
    private String patronymic;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "payment")
    private String payment;

    @Column(name = "start_date")
    private LocalDate startdate;

    @Column(name = "end_date")
    private LocalDate enddate;

    @Column(name = "reserv_date")
    private LocalDateTime reservdate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type_meal")
    private Type_meal type_meal;

    @Column(name = "num_adult")
    private int numAdult;

    @Column(name = "num_child")
    private int numChild;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_room")
    private Room room;

    @Column(name = "cost")
    private double cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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


    public LocalDateTime getReservdate() {
        return reservdate;
    }

    public void setReservdate(LocalDateTime reservdate) {
        this.reservdate = reservdate;
    }

    public Type_meal getType_meal() {
        return type_meal;
    }

    public void setType_meal(Type_meal type_meal) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
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

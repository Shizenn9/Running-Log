package com.nel.workoutlog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private double distance;
    private String pace;

    public Workout() {
        // JPA requires an empty constructor
    }

    public Workout(String date, double distance, String pace) {
        this.date = date;
        this.distance = distance;
        this.pace = pace;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getDistance() {
        return distance;
    }

    public String getPace() {
        return pace;
    }

    // setters ⬇️⬇️⬇️
    public void setDate(String date) {
        this.date = date;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }
}



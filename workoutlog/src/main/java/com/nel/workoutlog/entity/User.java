package com.nel.workoutlog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // ✅ REQUIRED: no-args constructor (JPA needs this)
    public User() {}

    // Optional convenience constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ✅ GETTERS & SETTERS (this fixes your AuthController errors)

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

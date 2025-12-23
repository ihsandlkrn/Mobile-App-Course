package com.afetlink.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "victims") // Creates a separate table named 'victims'
public class Victim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true) // Ensures email is unique within the victims table
    private String email;

    private String phone;
    private String password;

    // Default Constructor
    public Victim() {}

    // Parameterized Constructor
    public Victim(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
}
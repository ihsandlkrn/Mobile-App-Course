package com.afetlink.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "organizations") // Creates a separate table named 'organizations'
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Organization Name

    @Column(unique = true) // Ensures email is unique within the organizations table
    private String email;

    private String phone;
    private String password;

    // Default Constructor
    public Organization() {}

    // Parameterized Constructor
    public Organization(String name, String email, String phone, String password) {
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
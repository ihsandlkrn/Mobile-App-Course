package com.afetlink.backend.model;


import jakarta.persistence.*;
import java.util.List;




@Entity
@Table(name = "aid_requests")
public class AidRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String urgency;
    private String location;
    private String address;

    
    @ElementCollection
    private List<String> requestedPackages;

    // Default Constructor 
    public AidRequest() {}

    // Constructor
    public AidRequest(String description, String urgency, String location, String address, List<String> requestedPackages) {
        this.description = description;
        this.urgency = urgency;
        this.location = location;
        this.address = address;
        this.requestedPackages = requestedPackages;
    }

    // Getter and Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public List<String> getRequestedPackages() { return requestedPackages; }
    public void setRequestedPackages(List<String> requestedPackages) { this.requestedPackages = requestedPackages; }
}
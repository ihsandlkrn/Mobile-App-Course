package msku.ceng.madlab.testing.model;

import java.util.List;
import java.io.Serializable;

//It stores location, urgency, requested packages (food, heating), and address information.
public class AidRequest implements Serializable {
    private String description;
    private String urgency;
    private String location;
    private List<String> requestedPackages;
    private String address;

    // Constructor
    public AidRequest(String description, String urgency, String location, List<String> requestedPackages, String address) {
        this.description = description;
        this.urgency = urgency;
        this.location = location;
        this.requestedPackages = requestedPackages;
        this.address = address;
    }


    public String getDescription() { return description; }
    public String getUrgency() { return urgency; }
    public String getLocation() { return location; }
    public List<String> getRequestedPackages() { return requestedPackages; }
    public String getAddress() { return address; }
}
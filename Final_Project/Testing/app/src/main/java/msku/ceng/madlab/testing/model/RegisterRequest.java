package msku.ceng.madlab.testing.model;
//When registering, the system packages Name, Email, Phone Number, Password, and Role information.
public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role; // "Victim" or "Organization"

    public RegisterRequest(String name, String email, String phone, String password, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters are created automatically by Retrofit/Gson,

}
package msku.ceng.madlab.testing.model;

//When logging in, it packages the user's email, password, and role information.
public class LoginRequest {
    private String email;
    private String password;
    private String role; // "Victim" or "Organization"

    public LoginRequest(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
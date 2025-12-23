package com.afetlink.backend.controller;

import com.afetlink.backend.model.Victim;
import com.afetlink.backend.model.Organization;
import com.afetlink.backend.model.RegisterRequest;
import com.afetlink.backend.model.LoginRequest;
import com.afetlink.backend.repository.VictimRepository;
import com.afetlink.backend.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private VictimRepository victimRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    /**
     * REGISTRATION ENDPOINT
     * Saves the user to the correct table based on the selected role.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {

        // Case 1: Registering as a Victim
        if (request.getRole().equalsIgnoreCase("Victim")) {
            // Check if email already exists in Victims table
            if (victimRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists in Victims");
            }
            // Create and save new Victim
            Victim newVictim = new Victim(request.getName(), request.getEmail(), request.getPhone(), request.getPassword());
            victimRepository.save(newVictim);

        // Case 2: Registering as an Organization
        } else if (request.getRole().equalsIgnoreCase("Organization")) {
            // Check if email already exists in Organizations table
            if (organizationRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists in Organizations");
            }
            // Create and save new Organization
            Organization newOrg = new Organization(request.getName(), request.getEmail(), request.getPhone(), request.getPassword());
            organizationRepository.save(newOrg);

        } else {
            // Invalid Role
            return ResponseEntity.badRequest().body("Invalid Role selected");
        }

        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * LOGIN ENDPOINT
     * Validates credentials against the specific table for the selected role.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {

        // Logic: Check the role sent from Android app
        
        if (request.getRole().equalsIgnoreCase("Victim")) {
            // SEARCH ONLY IN VICTIMS TABLE
            Optional<Victim> victimOpt = victimRepository.findByEmail(request.getEmail());

            // Validate password
            if (victimOpt.isPresent() && victimOpt.get().getPassword().equals(request.getPassword())) {
                return ResponseEntity.ok("Login Successful as Victim");
            }

        } else if (request.getRole().equalsIgnoreCase("Organization")) {
            // SEARCH ONLY IN ORGANIZATIONS TABLE
            Optional<Organization> orgOpt = organizationRepository.findByEmail(request.getEmail());

            // Validate password
            if (orgOpt.isPresent() && orgOpt.get().getPassword().equals(request.getPassword())) {
                return ResponseEntity.ok("Login Successful as Organization");
            }
        }

        // If no match found in the specific table for that role:
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email, password, or role selection!");
    }
}
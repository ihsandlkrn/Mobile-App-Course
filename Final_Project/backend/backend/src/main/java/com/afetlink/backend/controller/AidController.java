package com.afetlink.backend.controller;

import com.afetlink.backend.model.AidRequest;
import com.afetlink.backend.repository.AidRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aid")
public class AidController {

    @Autowired
    private AidRequestRepository repository;

    
    @PostMapping("/create")
    public AidRequest createRequest(@RequestBody AidRequest request) {
        System.out.println("New Input: " + request.getDescription());
        return repository.save(request);
    }

   
    @GetMapping("/list")
    public List<AidRequest> getAllRequests() {
        return repository.findAll();
    }
}
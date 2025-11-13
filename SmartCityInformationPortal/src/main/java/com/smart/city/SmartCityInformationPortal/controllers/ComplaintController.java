package com.smart.city.SmartCityInformationPortal.controllers;

import Component.UpdateTitleOrDescription;
import com.smart.city.SmartCityInformationPortal.entities.Complaint;
import com.smart.city.SmartCityInformationPortal.services.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/complaint")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<?> newComplaint(@RequestBody Complaint complaint){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = complaintService.createComplaint(complaint, email);
        if(response){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-complaint/{complaintId}")
    public ResponseEntity<?> updateComplaint(@RequestBody UpdateTitleOrDescription updateTitleOrDescription, @PathVariable String complaintId){
        boolean response = complaintService.updateComplaint(updateTitleOrDescription, complaintId);
        if(response){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-complaint/{complaintId}")
    public ResponseEntity<?> deleteComplaint(@PathVariable String complaintId){
        boolean response = complaintService.deleteComplaint(complaintId);
        if(response){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-complaint/{complaintId}")
    public ResponseEntity<?> getComplaint(@PathVariable String complaintId){
        Complaint response = complaintService.getComplaint(complaintId);
        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

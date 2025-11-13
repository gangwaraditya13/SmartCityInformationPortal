package com.smart.city.SmartCityInformationPortal.controllers;

import com.smart.city.SmartCityInformationPortal.entities.*;
import com.smart.city.SmartCityInformationPortal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Collation
@RequestMapping("/city-admin")
public class CityAdminController {

    @Autowired
    private CityAdminService cityAdminService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UtilityService utilityService;


    /// oun city info
    @GetMapping("/city")
    public ResponseEntity<?> getCity(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        City cityInfo = cityService.getCityInfoAdmin(Email);
        if(cityInfo != null) {
            return new ResponseEntity<>(cityInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /// Utility
    @GetMapping("/get-utility")
    public ResponseEntity<?> getUtility(@RequestBody String utilityName){
        Utility utility = utilityService.utilityInfo(utilityName);
        if(utility != null){
            return new ResponseEntity<>(utility, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-utility/{cityId}")
    public ResponseEntity<?> newUtility(@RequestBody Utility utility,@PathVariable String cityId){
        boolean response = utilityService.newutility(utility, cityId);
        if(response) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update-utility")
    public ResponseEntity<?> updateUtility(@RequestBody Utility utility){
        boolean response = utilityService.updateutility(utility);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-utility")
    public ResponseEntity<?> deleteUtility(@RequestBody String utilityId){
        boolean response = utilityService.deleteutility(utilityId);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /// Hospital

    @GetMapping("/get-hospital")
    public ResponseEntity<?> getHospital(@RequestBody String hospitalName){
        Hospital hospital = hospitalService.hospitalInfo(hospitalName);
        if(hospital != null){
            return new ResponseEntity<>(hospital, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-hospital/{cityId}")
    public ResponseEntity<?> newHospital(@RequestBody Hospital hospital,@PathVariable String cityId){
        boolean response = hospitalService.newHospital(hospital, cityId);
        if(response) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update-hospital")
    public ResponseEntity<?> updateHospital(@RequestBody Hospital hospital){
        boolean response = hospitalService.updateHospital(hospital);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/add-facility/{hospitalName}")
    public ResponseEntity<?> addFacility(@PathVariable String hospitalName, @RequestBody String facility){
        boolean response = hospitalService.addFacility(hospitalName, facility);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/remove-facility/{hospitalName}")
    public ResponseEntity<?> removeFacility(@PathVariable String hospitalName, @RequestBody String facility){
        boolean response = hospitalService.deleteFacility(hospitalName, facility);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-hospital")
    public ResponseEntity<?> deleteHospital(@RequestBody String hospitalId){
        boolean response = hospitalService.deleteHospital(hospitalId);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /// school
    @GetMapping("/get-school")
    public ResponseEntity<?> getSchool(@RequestBody String utilityName){
        School school = schoolService.schoolInfo(utilityName);
        if(school != null){
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-school/{cityId}")
    public ResponseEntity<?> newSchool(@RequestBody School school,@PathVariable String cityId){
        boolean response = schoolService.newSchool(school, cityId);
        if(response) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update-school")
    public ResponseEntity<?> updateSchool(@RequestBody School school){
        boolean response = schoolService.updateSchool(school);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-school")
    public ResponseEntity<?> deleteSchool(@RequestBody String schoolId){
        boolean response = schoolService.deleteSchool(schoolId);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /// complaint
    @GetMapping("/get-pending-complaint")
    public ResponseEntity<?> getPendingComplaint(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Complaint> complaints = cityAdminService.pendingComplaint(email);
        if(complaints != null) {
            return new ResponseEntity<>(complaints,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/get-resolved-complaint")
    public ResponseEntity<?> getResolvedComplaint(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Complaint> complaints = cityAdminService.resolvedComplaint(email);
        if(complaints != null) {
            return new ResponseEntity<>(complaints,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/update-status/{complaintId}")
    public ResponseEntity<?> updateComplaintStatusAuto(@PathVariable String complaintId){
        boolean response = cityAdminService.updateComplaintStatus(complaintId);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /// user blocking

    @PutMapping("/block-user/{userEmail}")
    public ResponseEntity<?> blockUser(@PathVariable String userEmail){
        String emailCityAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = cityAdminService.toSuspend(emailCityAdmin, userEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/unblock-user/{userEmail}")
    public ResponseEntity<?> unblockUser(@PathVariable String userEmail){
        String emailCityAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = cityAdminService.toSuspend(emailCityAdmin, userEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

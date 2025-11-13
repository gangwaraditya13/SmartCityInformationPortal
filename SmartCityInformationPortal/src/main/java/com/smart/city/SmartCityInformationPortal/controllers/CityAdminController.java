package com.smart.city.SmartCityInformationPortal.controllers;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.Complaint;
import com.smart.city.SmartCityInformationPortal.entities.Utility;
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


    @GetMapping("/city")
    public ResponseEntity<?> getCity(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        City cityInfo = cityService.getCityInfoAdmin(Email);
        if(cityInfo != null) {
            return new ResponseEntity<>(cityInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}

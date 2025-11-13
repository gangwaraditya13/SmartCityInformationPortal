package com.smart.city.SmartCityInformationPortal.controllers;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CityService cityService;

    /// make ADMIN and CITY_ADMIN
    @PutMapping("/make-admin/{userEmail}")
    public ResponseEntity<?> makeAdmin(@PathVariable String userEmail){
        boolean response = adminService.updateUserRoll(userEmail, "ADMIN");
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/make-city-admin/{userEmail}")
    public ResponseEntity<?> makeCityAdmin(@PathVariable String userEmail){
        boolean response = adminService.updateUserRoll(userEmail, "CITY_ADMIN");
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/make-admin/{userEmail}")
    public ResponseEntity<?> removeAdmin(@PathVariable String userEmail){
        boolean response = adminService.removeRoll(userEmail, "ADMIN");
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/make-city-admin/{userEmail}")
    public ResponseEntity<?> removeCityAdmin(@PathVariable String userEmail){
        boolean response = adminService.removeRoll(userEmail, "CITY_ADMIN");
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /// city
    @GetMapping("/all-city")
    public ResponseEntity<?> getAllCity(){
        List<City> allCityInfo = cityService.getAllCityInfo();
        return new ResponseEntity<>(allCityInfo, HttpStatus.OK);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> getCity(@PathVariable String cityId){
        City cityInfo = cityService.getCityInfo(cityId);
        if(cityInfo != null) {
            return new ResponseEntity<>(cityInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new-city")
    public ResponseEntity<?> addCity(@RequestBody String cityName){
        boolean response = cityService.newCity(cityName);
        if(response){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-city-name/{cityId}")
    public ResponseEntity<?> updateCity(@RequestBody String cityName, @PathVariable String cityId){
        boolean response = cityService.updateCity(cityName, cityId);
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/update-city-name/{cityId}")
    public ResponseEntity<?> updateUser(@RequestBody String cityName, @PathVariable String cityId){
        boolean response = cityService.updateCity(cityName, cityId);
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    /// user blocking

    @PutMapping("/block-user/{userEmail}")
    public ResponseEntity<?> blockUser(@PathVariable String userEmail){
        String emailCityAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = adminService.toSuspend(emailCityAdmin, userEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/unblock-user/{userEmail}")
    public ResponseEntity<?> unblockUser(@PathVariable String userEmail){
        String emailCityAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = adminService.toSuspend(emailCityAdmin, userEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

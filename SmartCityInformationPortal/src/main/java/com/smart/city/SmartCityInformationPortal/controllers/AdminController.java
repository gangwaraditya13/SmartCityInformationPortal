package com.smart.city.SmartCityInformationPortal.controllers;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.services.*;
import dto.city.CityDto;
import dto.city.CityNameDot;
import dto.city.CityResponseDto;
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

    @PutMapping("/remove-admin/{userEmail}")
    public ResponseEntity<?> removeAdmin(@PathVariable String userEmail){
        boolean response = adminService.removeRoll(userEmail, "ADMIN");
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/remove-city-admin/{userEmail}")
    public ResponseEntity<?> removeCityAdmin(@PathVariable String userEmail){
        boolean response = adminService.removeRoll(userEmail, "CITY_ADMIN");
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /// city
    @GetMapping("/allcity")
    public ResponseEntity<?> getAllCity(){
        List<City> allCityInfo = cityService.getAllCityInfo();
        return new ResponseEntity<>(allCityInfo, HttpStatus.OK);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> getCity(@PathVariable String cityId){
        CityResponseDto cityInfo = cityService.getCityInfo(cityId);
        if(cityInfo != null) {
            return new ResponseEntity<>(cityInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new-city")
    public ResponseEntity<?> createCity(@RequestBody City city){
        CityDto response = cityService.newCity(city.getCityName());
        if(response != null){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update-city-name/{cityId}")
    public ResponseEntity<?> updateCity(@RequestBody CityNameDot cityName, @PathVariable String cityId){
        boolean response = cityService.updateCity(cityName.getName(), cityId);
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
        boolean response = adminService.toResume(emailCityAdmin, userEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

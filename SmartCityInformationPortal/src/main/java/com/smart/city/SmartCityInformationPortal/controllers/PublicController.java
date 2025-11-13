package com.smart.city.SmartCityInformationPortal.controllers;

import Component.DemoUser;
import Component.UserLogin;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.services.CityService;
import com.smart.city.SmartCityInformationPortal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @PostMapping("/signup/{cityId}")
    public ResponseEntity<?> signup(@RequestBody User user, @PathVariable String cityId){
        boolean check = userService.saveUser(user);
        if(check){
            boolean response = userService.saveNewUser(user, cityId);
            if(response){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin){

        DemoUser response = userService.saveUser(userLogin);

        if(response != null){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/city")
    public ResponseEntity<?> createCity(@RequestBody City city){
        boolean response = cityService.newCity(city.getCityName());
        if(response){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}

package com.smart.city.SmartCityInformationPortal.controllers;

import dto.user.TokenResponseDto;
import dto.user.UserLoginRequestDto;
import com.smart.city.SmartCityInformationPortal.services.CityService;
import com.smart.city.SmartCityInformationPortal.services.UserService;
import dto.user.UserRequestDto;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDto user){
        boolean check = userService.checkUser(user);
        if(check){
            boolean response = userService.saveNewUser(user);
            if(response){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto){

        TokenResponseDto response = userService.loginToken(userLoginRequestDto);

        if(response != null){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}

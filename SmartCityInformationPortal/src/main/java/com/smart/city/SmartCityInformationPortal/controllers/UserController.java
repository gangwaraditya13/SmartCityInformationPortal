package com.smart.city.SmartCityInformationPortal.controllers;

import Component.UpdateGmailOrUserName;
import Component.responceUser;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUser(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        User info = userService.getInfo(Email);
        if(info != null){
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updateUser(UpdateGmailOrUserName UpdateGmailOrUserName){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = userService.updateUser(UpdateGmailOrUserName, Email);
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody String passwordRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Email = authentication.getName();
        boolean check = userService.deleteUser(passwordRequest, Email);
        if(check){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

}

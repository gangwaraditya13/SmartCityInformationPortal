package com.smart.city.SmartCityInformationPortal.controllers;

import com.smart.city.SmartCityInformationPortal.services.CityService;
import com.smart.city.SmartCityInformationPortal.services.UserService;
import com.smart.city.SmartCityInformationPortal.dto.city.CityNameDot;
import com.smart.city.SmartCityInformationPortal.dto.user.UpdateGmailOrUserName;
import com.smart.city.SmartCityInformationPortal.dto.user.PasswordResetDto;
import com.smart.city.SmartCityInformationPortal.dto.user.RequestPasswordDot;
import com.smart.city.SmartCityInformationPortal.dto.user.UserDto;
import com.smart.city.SmartCityInformationPortal.dto.user.UserResponseCityDto;
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

    @Autowired
    private CityService cityService;

    @GetMapping("/getuser")
    public ResponseEntity<UserDto> getUser(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto info = userService.getInfo(Email);
        if(info != null){
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody PasswordResetDto passwordResetDto){
        boolean response = userService.updatePassword(passwordResetDto);
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateGmailOrUserName updateGmailOrUserName){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = userService.updateUser(updateGmailOrUserName, Email);
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody RequestPasswordDot passwordRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Email = authentication.getName();
        boolean check = userService.deleteUser(passwordRequest.getPassword(), Email);
        if(check){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/own-city-info")
    public ResponseEntity<?> getOwnCityInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto userInfo = userService.getInfo(email);
        UserResponseCityDto ownCityInfo = cityService.getOwnCityInfo(userInfo.getCity());
        if(ownCityInfo != null) {
            return new ResponseEntity<>(ownCityInfo,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/other-city-info")
    public ResponseEntity<?> getOtherCityInfo(@RequestBody CityNameDot cityNameDot){

        UserResponseCityDto ownCityInfo = cityService.getOwnCityInfo(cityNameDot.getName());
        if(ownCityInfo != null) {
            return new ResponseEntity<>(ownCityInfo,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

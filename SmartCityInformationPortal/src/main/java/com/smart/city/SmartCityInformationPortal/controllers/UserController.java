package com.smart.city.SmartCityInformationPortal.controllers;

import com.smart.city.SmartCityInformationPortal.dto.city_updates.CityUpdatesResponseDto;
import com.smart.city.SmartCityInformationPortal.dto.user.ImageUpdateRequestDto;
import com.smart.city.SmartCityInformationPortal.services.CityService;
import com.smart.city.SmartCityInformationPortal.services.CityUpdatesServices;
import com.smart.city.SmartCityInformationPortal.services.CloudinaryImageService;
import com.smart.city.SmartCityInformationPortal.services.UserService;
import com.smart.city.SmartCityInformationPortal.dto.city.CityNameDto;
import com.smart.city.SmartCityInformationPortal.dto.user.UpdateGmailOrUserName;
import com.smart.city.SmartCityInformationPortal.dto.user.PasswordResetDto;
import com.smart.city.SmartCityInformationPortal.dto.user.RequestPasswordDot;
import com.smart.city.SmartCityInformationPortal.dto.user.UserDto;
import com.smart.city.SmartCityInformationPortal.dto.user.UserResponseCityDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.Map;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Autowired
    private CityUpdatesServices cityUpdatesServices;

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

    @PostMapping("/delete-user")
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
    public ResponseEntity<?> getOtherCityInfo(@RequestBody CityNameDto cityNameDto){

        UserResponseCityDto ownCityInfo = cityService.getOwnCityInfo(cityNameDto.getName());
        if(ownCityInfo != null) {
            return new ResponseEntity<>(ownCityInfo,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /// Image
    @PostMapping(
            value = "/image-upload",
            consumes = "multipart/form-data"
    )
    @Operation(
            summary = "Upload profile image",
            description = "Upload user profile image to Cloudinary"
    )
    public ResponseEntity<Map<String, Object>> uploadImage(
            @Parameter(
                    description = "Image file (jpg, png, jpeg)",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestPart("image") MultipartFile file
    ) {
        Map<String, Object> data = cloudinaryImageService.uploadImage(file);
        if (!data.isEmpty()) {
            return new ResponseEntity<>(data, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



    /// first hit /image-upload endpoint then send the ImageUpdateRequestUrl to /update-profile-image for profile pic
  /// Image Update
    @PutMapping("/update-profile-image")
    public ResponseEntity<?> updateImage(@RequestBody ImageUpdateRequestDto imageUpdateRequestDto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean done = userService.updateUser(imageUpdateRequestDto, email);
        if(done){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete-profile-image")
    public ResponseEntity<?> deleteImage(@RequestBody ImageUpdateRequestDto imageUpdateRequestDto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean done = userService.deleteUserProfilePic(imageUpdateRequestDto,email);
        if(done){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /// get cityUpdates make here to get all updates

    @GetMapping("/city-updates")
    public ResponseEntity<?> getCityUpdates(){
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            CityUpdatesResponseDto cityUpdates = cityUpdatesServices.getCityUpdates(email);
            return new ResponseEntity<>(cityUpdates,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

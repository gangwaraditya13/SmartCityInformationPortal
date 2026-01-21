package com.smart.city.SmartCityInformationPortal.controllers;

import com.smart.city.SmartCityInformationPortal.entities.*;
import com.smart.city.SmartCityInformationPortal.services.*;
import com.smart.city.SmartCityInformationPortal.dto.hospital.HospitalFacilityDto;
import com.smart.city.SmartCityInformationPortal.dto.city.CityDto;
import com.smart.city.SmartCityInformationPortal.dto.hospital.HospitalIdDto;
import com.smart.city.SmartCityInformationPortal.dto.hospital.HospitalDto;
import com.smart.city.SmartCityInformationPortal.dto.school.SchoolDto;
import com.smart.city.SmartCityInformationPortal.dto.school.SchoolIdDto;
import com.smart.city.SmartCityInformationPortal.dto.school.SchoolNameDto;
import com.smart.city.SmartCityInformationPortal.dto.utility.UtilityDto;
import com.smart.city.SmartCityInformationPortal.dto.utility.UtilityIdDto;
import com.smart.city.SmartCityInformationPortal.dto.utility.UtilityUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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


    /// own city info
    @GetMapping("/city")
    public ResponseEntity<?> getCity(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        CityDto cityInfo = cityService.getCityInfoAdmin(Email);
        if(cityInfo != null) {
            return new ResponseEntity<>(cityInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /// Utility
    @PostMapping("/get-utility")
    public ResponseEntity<?> getUtility(@RequestBody UtilityIdDto utilityId){
        Utility utility = utilityService.utilityInfo(utilityId.getUtilityId());
        if(utility != null){
            return new ResponseEntity<>(utility, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-utility")
    public ResponseEntity<?> newUtility(@RequestBody UtilityDto utility){
        String cityAdminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Utility response = utilityService.newutility(utility, cityAdminEmail);
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update-utility")
    public ResponseEntity<?> updateUtility(@RequestBody UtilityUpdateDto utility){
        boolean response = utilityService.updateutility(utility);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-utility")
    public ResponseEntity<?> deleteUtility(@RequestBody UtilityIdDto utilityId){
        boolean response = utilityService.deleteutility(utilityId.getUtilityId());
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /// Hospital

    @PostMapping("/get-hospital")
    public ResponseEntity<?> getHospital(@RequestBody String hospitalName){
        Hospital hospital = hospitalService.hospitalInfo(hospitalName);
        if(hospital != null){
            return new ResponseEntity<>(hospital, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-hospital")
    public ResponseEntity<?> newHospital(@RequestBody HospitalDto hospitalDto){
        String cityAdminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = hospitalService.newHospital(hospitalDto, cityAdminEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update-hospital")
    public ResponseEntity<?> updateHospital(@RequestBody HospitalDto hospitalDto){
        boolean response = hospitalService.updateHospital(hospitalDto);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/add-facility")
    public ResponseEntity<?> addFacility(@RequestBody HospitalFacilityDto hospitalFacilityDto){
        boolean response = hospitalService.addFacility(hospitalFacilityDto);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/remove-facility")
    public ResponseEntity<?> removeFacility(@RequestBody HospitalFacilityDto hospitalFacilityDto){
        boolean response = hospitalService.deleteFacility(hospitalFacilityDto);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-hospital")
    public ResponseEntity<?> deleteHospital(@RequestBody HospitalIdDto hospitalIdDto){
        boolean response = hospitalService.deleteHospital(hospitalIdDto.getHospitalId());
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /// school
    @PostMapping("/get-school")
    public ResponseEntity<?> getSchool(@RequestBody SchoolNameDto schoolNameDto){
        School school = schoolService.schoolInfo(schoolNameDto.getSchoolName());
        if(school != null){
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-school")
    public ResponseEntity<?> newSchool(@RequestBody SchoolDto schoolDto){
        String admainEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean response = schoolService.newSchool(schoolDto, admainEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update-school")
    public ResponseEntity<?> updateSchool(@RequestBody SchoolDto schoolDto){
        boolean response = schoolService.updateSchool(schoolDto);

        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-school")
    public ResponseEntity<?> deleteSchool(@RequestBody SchoolIdDto schoolIdDto){
        boolean response = schoolService.deleteSchool(schoolIdDto.getSchoolId());
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
        boolean response = cityAdminService.toResume(emailCityAdmin, userEmail);
        if(response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

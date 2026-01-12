package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.School;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.SchoolRepository;
import com.smart.city.SmartCityInformationPortal.repository.UserRepository;
import com.smart.city.SmartCityInformationPortal.dto.school.SchoolDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public boolean newSchool(SchoolDto newSchoolDto, String adminEmail){

        User cityAdmin = userRepository.findByEmail(adminEmail);

        School checkSchool = schoolRepository.findBySchoolName(newSchoolDto.getSchoolName());
        if(checkSchool==null){
            School school = modelMapper.map(newSchoolDto, School.class);
            School saved = schoolRepository.save(school);
            Optional<City> city = cityRepository.findById(cityAdmin.getCity());
            city.get().getCitySchools().add(saved);
            cityRepository.save(city.get());
            return true;
        }
        return false;
    }

    public boolean updateSchool(SchoolDto schoolDto){
        boolean anyChange = false;
        School checkSchool = schoolRepository.findBySchoolName(schoolDto.getSchoolName());
        if(checkSchool.getSchoolName().equals(schoolDto.getSchoolName())){
            checkSchool.setSchoolName(schoolDto.getSchoolName());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }
        if(checkSchool.getOwnership().equals(schoolDto.getOwnership())){
            checkSchool.setOwnership(schoolDto.getOwnership());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }
        if(checkSchool.getCategory().equals(schoolDto.getCategory())){
            checkSchool.setCategory(schoolDto.getCategory());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }
        if(checkSchool.getSchoolAddress().equals(schoolDto.getSchoolAddress())){
            checkSchool.setSchoolAddress(schoolDto.getSchoolAddress());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }
        if(checkSchool.getSchoolContact().equals(schoolDto.getSchoolContact())){
            checkSchool.setSchoolContact(schoolDto.getSchoolContact());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }
        return anyChange;
    }

    public boolean deleteSchool(String schoolId){
        boolean checkSchool = schoolRepository.findById(schoolId);
        if(checkSchool){
            schoolRepository.deleteById(schoolId);
            return true;
        }else{
            return false;
        }
    }

    public School schoolInfo(String schoolName){
        School checkSchool = schoolRepository.findBySchoolName(schoolName);
        return checkSchool;
    }

}

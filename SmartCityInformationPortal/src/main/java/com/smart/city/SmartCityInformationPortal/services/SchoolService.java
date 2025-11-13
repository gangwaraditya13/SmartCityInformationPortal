package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.School;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CityRepository cityRepository;

    public boolean newSchool(School newSchool, String cityId){
        School checkSchool = schoolRepository.findByName(newSchool.getSchoolName());
        if(checkSchool!=null){
            School saved = schoolRepository.save(newSchool);
            City city = cityRepository.findById(cityId);
            city.getCitySchools().add(saved);
            cityRepository.save(city);
            return true;
        }
        return false;
    }

    public boolean updateSchool(School school){
        boolean anyChange = false;
        School checkSchool = schoolRepository.findByName(school.getSchoolName());
        if(checkSchool.getSchoolAddress() != school.getSchoolAddress()){
            checkSchool.setSchoolAddress(school.getSchoolAddress());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }
        if(checkSchool.getSchoolContact() != school.getSchoolContact()){
            checkSchool.setSchoolContact(school.getSchoolContact());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }
        if(checkSchool.getSchoolType() != school.getSchoolType()){
            checkSchool.setSchoolType(school.getSchoolType());
            schoolRepository.save(checkSchool);
            anyChange = true;
        }if(checkSchool.getSchoolName() != school.getSchoolName()){
            checkSchool.setSchoolName(school.getSchoolName());
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
        School checkSchool = schoolRepository.findByName(schoolName);
        return checkSchool;
    }

}

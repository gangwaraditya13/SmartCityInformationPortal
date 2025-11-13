package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.Hospital;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private CityRepository cityRepository;

    public boolean newHospital(Hospital newHospital, String cityId){
        Hospital checkSchool = hospitalRepository.findByHospitalName(newHospital.getHospitalName());
        if(checkSchool==null){
            Hospital saved = hospitalRepository.save(newHospital);
            Optional<City> city = cityRepository.findById(cityId);
            city.get().getCityHospitals().add(saved);
            cityRepository.save(city.get());
            return true;
        }
        return false;
    }

    public boolean updateHospital(Hospital hospital){
        boolean anyChange = false;
        Hospital checkHospital = hospitalRepository.findByHospitalName(hospital.getHospitalName());
        if(checkHospital != null) {
            if (checkHospital.getHospitalAddress() != hospital.getHospitalAddress()) {
                checkHospital.setHospitalAddress(hospital.getHospitalAddress());
                hospitalRepository.save(checkHospital);
                anyChange = true;
            }
            if (checkHospital.getHospitalContact() != hospital.getHospitalContact()) {
                checkHospital.setHospitalContact(hospital.getHospitalContact());
                hospitalRepository.save(checkHospital);
                anyChange = true;
            }
            if (checkHospital.getHospitalName() != hospital.getHospitalName()) {
                checkHospital.setHospitalName(hospital.getHospitalName());
                hospitalRepository.save(checkHospital);
                anyChange = true;
            }
        }
        return anyChange;
    }

    public boolean addFacility(String hospitalName,String facility){
        Hospital checkHospital = hospitalRepository.findByHospitalName(hospitalName);
        List<String> collect = checkHospital.getHospitalFacilities().stream().filter(x -> x.equals(facility)).collect(Collectors.toList());
        if(collect.isEmpty()){
            checkHospital.getHospitalFacilities().add(facility);
            hospitalRepository.save(checkHospital);
            return true;
        }
        return false;
    }

    public boolean deleteFacility(String hospitalName,String facility){
        Hospital checkHospital = hospitalRepository.findByHospitalName(hospitalName);
        List<String> collect = checkHospital.getHospitalFacilities().stream().filter(x -> x.equals(facility)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            checkHospital.getHospitalFacilities().remove(facility);
            hospitalRepository.save(checkHospital);
            return true;
        }
        return false;
    }

    public boolean deleteHospital(String hospitalId){
        Optional<Hospital> checkHospital = hospitalRepository.findById(hospitalId);
        if(!checkHospital.isEmpty()){
            hospitalRepository.deleteById(hospitalId);
            return true;
        }else{
            return false;
        }
    }

    public Hospital hospitalInfo(String hospitalName){
        Hospital checkHospital = hospitalRepository.findByHospitalName(hospitalName);
        return checkHospital;
    }
}

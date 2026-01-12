package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.Hospital;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.HospitalRepository;
import com.smart.city.SmartCityInformationPortal.repository.UserRepository;
import com.smart.city.SmartCityInformationPortal.dto.hospital.HospitalFacilityDto;
import com.smart.city.SmartCityInformationPortal.dto.hospital.HospitalDto;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public boolean newHospital(HospitalDto hospitalDto, String cityAdminEmail){
        User cityAdmin = userRepository.findByEmail(cityAdminEmail);
        Hospital checkSchool = hospitalRepository.findByHospitalName(hospitalDto.getHospitalName());
        if(checkSchool==null){
            Hospital hospital = modelMapper.map(hospitalDto, Hospital.class);
            Hospital saved = hospitalRepository.save(hospital);
            City city = cityRepository.findByCityName(cityAdmin.getCity()).orElseThrow();
            city.getCityHospitals().add(saved);
            cityRepository.save(city);
            return true;
        }
        return false;
    }

    public boolean updateHospital(HospitalDto hospitalDto){
        boolean anyChange = false;
        Hospital checkHospital = hospitalRepository.findByHospitalName(hospitalDto.getHospitalName());
        if(checkHospital != null) {
            if (!checkHospital.getHospitalAddress().equals(hospitalDto.getHospitalAddress())) {
                checkHospital.setHospitalAddress(hospitalDto.getHospitalAddress());
                hospitalRepository.save(checkHospital);
                anyChange = true;
            }
            if (checkHospital.getHospitalContact().equals(hospitalDto.getHospitalContact())) {
                checkHospital.setHospitalContact(hospitalDto.getHospitalContact());
                hospitalRepository.save(checkHospital);
                anyChange = true;
            }
            if (!checkHospital.getHospitalName().equals(hospitalDto.getHospitalName())) {
                checkHospital.setHospitalName(hospitalDto.getHospitalName());
                hospitalRepository.save(checkHospital);
                anyChange = true;
            }
        }
        return anyChange;
    }

    public boolean addFacility(HospitalFacilityDto hospitalFacilityDto){
        Hospital checkHospital = hospitalRepository.findByHospitalName(hospitalFacilityDto.getHospitalName());
        List<String> collect = checkHospital.getHospitalFacilities().stream().filter(x -> x.equals(hospitalFacilityDto.getFacility())).collect(Collectors.toList());
        if(collect.isEmpty()){
            checkHospital.getHospitalFacilities().add(hospitalFacilityDto.getFacility());
            hospitalRepository.save(checkHospital);
            return true;
        }
        return false;
    }

    public boolean deleteFacility(HospitalFacilityDto hospitalFacilityDto){
        Hospital checkHospital = hospitalRepository.findByHospitalName(hospitalFacilityDto.getHospitalName());
        List<String> collect = checkHospital.getHospitalFacilities().stream().filter(x -> x.equals(hospitalFacilityDto.getFacility())).collect(Collectors.toList());
        if(!collect.isEmpty()){
            checkHospital.getHospitalFacilities().remove(hospitalFacilityDto.getFacility());
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

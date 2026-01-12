package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.entities.Utility;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.UserRepository;
import com.smart.city.SmartCityInformationPortal.repository.UtilityRepository;
import dto.utility.UtilityDto;
import dto.utility.UtilityUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UtilityService {

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public boolean newutility(UtilityDto newutility, String cityAdminEmail){
        try{
            User cityAdmin = userRepository.findByEmail(cityAdminEmail);
            Utility utility = modelMapper.map(newutility, Utility.class);
            Utility saved = utilityRepository.save(utility);
            City city = cityRepository.findById(cityAdmin.getCity()).orElseThrow();
            city.getCityUtilities().add(saved);
            cityRepository.save(city);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean updateutility(UtilityUpdateDto utility){
        boolean anyChange = false;
        Utility checkutility = utilityRepository.findById(utility.getUtilityId()).orElseThrow();
        if(checkutility.getUtilityAddress().equals(utility.getUtilityAddress())){
            checkutility.setUtilityAddress(utility.getUtilityAddress());
            utilityRepository.save(checkutility);
            anyChange = true;
        }
        if(checkutility.getUtilityContact().equals(utility.getUtilityContact())){
            checkutility.setUtilityContact(utility.getUtilityContact());
            utilityRepository.save(checkutility);
            anyChange = true;
        }
        if(checkutility.getUtilityDepartment().equals(utility.getUtilityDepartment())){
            checkutility.setUtilityDepartment(utility.getUtilityDepartment());
            utilityRepository.save(checkutility);
            anyChange = true;
        }if(checkutility.getUtilityDepartmentOfficer().equals(utility.getUtilityDepartmentOfficer())){
            checkutility.setUtilityDepartmentOfficer(utility.getUtilityDepartmentOfficer());
            utilityRepository.save(checkutility);
            anyChange = true;
        }
        return anyChange;
    }

    public boolean deleteutility(String utilityId){
        Optional<Utility> checkutility = utilityRepository.findById(utilityId);
        if(!checkutility.isEmpty()){
            utilityRepository.deleteById(utilityId);
            return true;
        }else{
            return false;
        }
    }

    public Utility utilityInfo(String utilityId){
        Utility checkutility = utilityRepository.findById(utilityId).orElseThrow();
        return checkutility;
    }


}

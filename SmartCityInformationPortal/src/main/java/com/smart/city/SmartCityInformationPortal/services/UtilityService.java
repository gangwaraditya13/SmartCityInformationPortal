package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.Utility;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.UtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilityService {

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private CityRepository cityRepository;

    public boolean newutility(Utility newutility, String cityId){
        Utility checkutility = utilityRepository.findByUtilityDepartment(newutility.getUtilityDepartment());
        if(checkutility!=null){
            Utility saved = utilityRepository.save(newutility);
            Optional<City> city = cityRepository.findById(cityId);
            city.get().getCityUtilities().add(saved);
            cityRepository.save(city.get());
            return true;
        }
        return false;
    }

    public boolean updateutility(Utility utility){
        boolean anyChange = false;
        Utility checkutility = utilityRepository.findByUtilityDepartment(utility.getUtilityDepartment());
        if(checkutility.getUtilityAddress() != utility.getUtilityAddress()){
            checkutility.setUtilityAddress(utility.getUtilityAddress());
            utilityRepository.save(checkutility);
            anyChange = true;
        }
        if(checkutility.getUtilityContact() != utility.getUtilityContact()){
            checkutility.setUtilityContact(utility.getUtilityContact());
            utilityRepository.save(checkutility);
            anyChange = true;
        }
        if(checkutility.getUtilityDepartment()!= utility.getUtilityDepartment()){
            checkutility.setUtilityDepartment(utility.getUtilityDepartment());
            utilityRepository.save(checkutility);
            anyChange = true;
        }if(checkutility.getUtilityDepartmentOfficer()!= utility.getUtilityDepartmentOfficer()){
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

    public Utility utilityInfo(String utilityName){
        Utility checkutility = utilityRepository.findByUtilityDepartment(utilityName);
        return checkutility;
    }


}

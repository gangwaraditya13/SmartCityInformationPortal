package com.smart.city.SmartCityInformationPortal.services;

import Component.responceUser;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.reposetry.CityRepository;
import com.smart.city.SmartCityInformationPortal.reposetry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserService userService;


    public boolean newCity(String cityName){
        City city = new City();
        City checkCity = cityRepository.findByCityName(cityName);
        if(checkCity != null){
            city.setCityName(cityName);
            cityRepository.save(city);
            return true;
        }
        return false;
    }

    public boolean updateCity(String cityName,String cityId){
        City checkCity = cityRepository.findById(cityId);
        if(checkCity != null){
            checkCity.setCityName(cityName);
            cityRepository.save(checkCity);
            return true;
        }
        return false;
    }

    public City getCityInfo(String cityId){
        City demo = new City();
        City city = cityRepository.findById(cityId);
        if(city != null) {
            demo.setCityName(city.getCityName());
            demo.setCitySchools(city.getCitySchools());
            demo.setCityHospitals(city.getCityHospitals());
            demo.setCityUtilities(city.getCityUtilities());
            return demo;
        }
        return demo;
    }

    public City getCityInfoAdmin(String Email){

        responceUser info = userService.getInfo(Email);
        City city = cityRepository.findByCityName(info.getCity());
        if(city != null) {
            return city;
        }
        return city;
    }

    public List<City> getAllCityInfo(){
        List<City> allCity = cityRepository.findAll();
        return allCity;
    }

}

package com.smart.city.SmartCityInformationPortal.services;

import Component.responceUser;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<City> checkCity = cityRepository.findById(cityId);
        if(checkCity.isPresent()){
            checkCity.get().setCityName(cityName);
            cityRepository.save(checkCity.get());
            return true;
        }
        return false;
    }

    public City getCityInfo(String cityId){
        City demo = new City();
        Optional<City> city = cityRepository.findById(cityId);
        if(city.isPresent()) {
            demo.setCityName(city.get().getCityName());
            demo.setCitySchools(city.get().getCitySchools());
            demo.setCityHospitals(city.get().getCityHospitals());
            demo.setCityUtilities(city.get().getCityUtilities());
            return demo;
        }
        return demo;
    }

    public City getCityInfoAdmin(String Email){

        responceUser info = userService.getInfoAdmin(Email);
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

package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.dto.city.CityDto;
import com.smart.city.SmartCityInformationPortal.dto.city.CityResponseDto;
import com.smart.city.SmartCityInformationPortal.dto.user.UserDto;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.dto.user.UserResponseCityDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CityService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public CityDto newCity(String cityName){
        City city = new City();
        Optional<City> checkCity = cityRepository.findByCityName(cityName);
        if(!checkCity.isPresent()){
            city.setCityName(cityName);
            cityRepository.save(city);
            return modelMapper.map(city, CityDto.class);
        }
        return null;
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

    public CityResponseDto getCityInfo(String cityId){
        City city = cityRepository.findById(cityId).orElseThrow();

        return modelMapper.map(city, CityResponseDto.class);
    }

    public UserResponseCityDto getOwnCityInfo(String cityName){
        Optional<City> city = cityRepository.findByCityName(cityName);
        return modelMapper.map(city, UserResponseCityDto.class);
    }

    public CityDto getCityInfoAdmin(String Email){

        UserDto info = userService.getInfoAdmin(Email);
        City city = cityRepository.findByCityName(info.getCity()).orElseThrow();

        return  modelMapper.map(city,CityDto.class);
    }

    public List<City> getAllCityInfo(){
        List<City> allCity = cityRepository.findAll();
        return allCity;
    }

}

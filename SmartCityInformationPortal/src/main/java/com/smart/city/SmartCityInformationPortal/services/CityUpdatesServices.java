package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.dto.city_updates.CityUpdatesRequestDto;
import com.smart.city.SmartCityInformationPortal.dto.city_updates.CityUpdatesResponseDto;
import com.smart.city.SmartCityInformationPortal.dto.city_updates.CityUpdatesUpdateImageDto;
import com.smart.city.SmartCityInformationPortal.dto.city_updates.CityUpdatesUpdateTitleDescriptionDto;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.CityUpdates;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.CityUpdatesRepository;
import com.smart.city.SmartCityInformationPortal.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityUpdatesServices {

    @Autowired
    private CityUpdatesRepository cityUpdatesRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    /// all controllers n=make in city-admin and city-admin service
    @Transactional
    public boolean createCityUpdates(CityUpdatesRequestDto cityUpdatesRequestDto, String email){
        try{
            User user = userRepository.findByEmail(email);
            City city = cityRepository.findByCityName(user.getCity()).orElseThrow();
            if(city.getCityUpdates().size() < 8) {
                CityUpdates cityUpdates = modelMapper.map(cityUpdatesRequestDto, CityUpdates.class);
                CityUpdates cityUpdates1 = cityUpdatesRepository.save(cityUpdates);
                city.getCityUpdates().addFirst(cityUpdates1);
                cityRepository.save(city);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.err.println("CityUpdatesService : create : "+e.getMessage());
            return false;
        }
    }

    public CityUpdatesResponseDto getCityUpdates(String email){
        try {
            User user = userRepository.findByEmail(email);
            City city = cityRepository.findByCityName(user.getCity()).orElseThrow();

            CityUpdatesResponseDto cityUpdatesResponseDto = modelMapper.map(city, CityUpdatesResponseDto.class);
            return cityUpdatesResponseDto;
        } catch (Exception e) {
            throw new RuntimeException("city update service : get updates : "+e);
        }
    }

    public boolean updateCityUpdates(CityUpdatesUpdateTitleDescriptionDto cityUpdatesUpdateTitleDescriptionDto){
        try {
            CityUpdates cityUpdates = cityUpdatesRepository.findById(cityUpdatesUpdateTitleDescriptionDto.getId()).orElseThrow();
            if(!cityUpdates.getTitle().equals(cityUpdatesUpdateTitleDescriptionDto.getTitle())){
                cityUpdates.setTitle(cityUpdatesUpdateTitleDescriptionDto.getTitle());
                cityUpdatesRepository.save(cityUpdates);
                return true;
            }
            if(!cityUpdates.getDescription().equals(cityUpdatesUpdateTitleDescriptionDto.getDescription())){
                cityUpdates.setDescription(cityUpdatesUpdateTitleDescriptionDto.getDescription());
                cityUpdatesRepository.save(cityUpdates);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("CityUpdatesService : update title or description : "+e.getMessage());
            return false;
        }
    }

    public boolean updateCityUpdatesImage(CityUpdatesUpdateImageDto cityUpdatesUpdateImageDto){
        try {
            CityUpdates cityUpdates = cityUpdatesRepository.findById(cityUpdatesUpdateImageDto.getId()).orElseThrow();
            if(cityUpdates.getProfileProductId() == null || cityUpdates.getProfileProductId().isEmpty() || cityUpdates.getProfileProductId().equals(cityUpdatesUpdateImageDto.getProfileProductId())){
                if(cityUpdates.getProfileProductId()== null || cityUpdates.getProfileProductId().isEmpty()) {
                    cityUpdates.setProfileProductId(cityUpdatesUpdateImageDto.getProfileProductId());
                    cityUpdates.setProfilePhotoURL(cityUpdatesUpdateImageDto.getProfilePhotoURL());
                    cityUpdatesRepository.save(cityUpdates);
                }else{
                    cloudinaryImageService.deleteImage(cityUpdates.getProfileProductId());
                    cityUpdates.setProfileProductId(cityUpdatesUpdateImageDto.getProfileProductId());
                    cityUpdates.setProfilePhotoURL(cityUpdatesUpdateImageDto.getProfilePhotoURL());
                    cityUpdatesRepository.save(cityUpdates);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("CityUpdatesService : update image : "+e.getMessage());
            return false;
        }
    }

    public boolean deleteCityUpdate(String id){
        try {
            CityUpdates cityUpdates = cityUpdatesRepository.findById(id).orElseThrow();
            if(cityUpdates.getProfileProductId()== null || cityUpdates.getProfileProductId().isEmpty()) {
                cityUpdatesRepository.deleteById(id);
            }else{
                cloudinaryImageService.deleteImage(cityUpdates.getProfileProductId());
                cityUpdatesRepository.deleteById(id);
            }
            return true;
        }catch (Exception e){
            System.err.println("CityUpdatesService : delete : "+e.getMessage());
            return false;
        }
    }

}

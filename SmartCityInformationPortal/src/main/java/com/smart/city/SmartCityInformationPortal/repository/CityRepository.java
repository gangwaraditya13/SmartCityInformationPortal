package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CityRepository extends MongoRepository<City, String> {

    Optional<City> findById(String cityId);

    Optional<City> findByCityName(String cityName);

}

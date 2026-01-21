package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends MongoRepository<City, String> {

    Optional<City> findById(String cityId);

    Optional<City> findByCityName(String cityName);

    List<City> findByCityNameStartingWithIgnoreCase(String hint);

//    @Query(value = "{ 'cityName': { $regex: ?0, $options: 'i' } }",
//            fields = "{ 'cityName': 1 }")
//    List<City> findCitiesByRegex(String regex);

}

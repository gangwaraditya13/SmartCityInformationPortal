package com.smart.city.SmartCityInformationPortal.reposetry;

import com.smart.city.SmartCityInformationPortal.entities.City;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City, Object> {

    City findById(String cityId);

    City findByCityName(String cityName);

}

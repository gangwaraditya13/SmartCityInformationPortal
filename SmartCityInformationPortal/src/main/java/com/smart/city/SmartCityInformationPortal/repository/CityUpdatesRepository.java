package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.CityUpdates;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityUpdatesRepository extends MongoRepository<CityUpdates, String> {
}

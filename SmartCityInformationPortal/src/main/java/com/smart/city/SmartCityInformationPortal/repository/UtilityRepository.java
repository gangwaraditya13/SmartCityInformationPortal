package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.Utility;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UtilityRepository extends MongoRepository<Utility, Object> {
    Utility findByUtilityDepartment(String name);

    boolean findById(String id);
}

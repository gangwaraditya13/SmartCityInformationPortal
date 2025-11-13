package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.Utility;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UtilityRepository extends MongoRepository<Utility, String> {
    Utility findByUtilityDepartment(String name);

    Optional<Utility> findById(String id);
}

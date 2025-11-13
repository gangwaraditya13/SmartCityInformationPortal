package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.Hospital;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HospitalRepository extends MongoRepository<Hospital, Object> {
    Hospital findByName(@NonNull String schoolName);

    boolean findById(String id);
}

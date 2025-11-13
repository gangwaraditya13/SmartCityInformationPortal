package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.Hospital;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HospitalRepository extends MongoRepository<Hospital, String> {
    Hospital findByHospitalName(@NonNull String schoolName);

    Optional<Hospital> findById(String id);
}

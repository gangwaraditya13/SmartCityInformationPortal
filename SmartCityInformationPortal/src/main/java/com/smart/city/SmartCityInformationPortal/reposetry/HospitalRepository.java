package com.smart.city.SmartCityInformationPortal.reposetry;

import com.smart.city.SmartCityInformationPortal.entities.Hospital;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HospitalRepository extends MongoRepository<Hospital, Object> {
    Hospital findByName(@NonNull String schoolName);

    boolean findById(String id);
}

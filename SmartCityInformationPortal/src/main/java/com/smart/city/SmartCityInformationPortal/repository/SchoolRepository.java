package com.smart.city.SmartCityInformationPortal.repository;

import com.smart.city.SmartCityInformationPortal.entities.School;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchoolRepository extends MongoRepository<School,Object> {
    School findBySchoolName(@NonNull String schoolName);

    boolean findById(String id);

    void deleteById(String id);
}

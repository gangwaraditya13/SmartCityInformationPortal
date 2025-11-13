package com.smart.city.SmartCityInformationPortal.reposetry;

import com.smart.city.SmartCityInformationPortal.entities.School;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SchoolRepository extends MongoRepository<School,Object> {
    School findByName(@NonNull String schoolName);

    boolean findById(String id);

    void deleteById(String id);
}

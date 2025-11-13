package com.smart.city.SmartCityInformationPortal.reposetry;

import com.smart.city.SmartCityInformationPortal.entities.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ComplaintRepository extends MongoRepository<Complaint, Object> {
    Complaint findById(String id);
}

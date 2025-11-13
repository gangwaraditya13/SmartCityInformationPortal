package com.smart.city.SmartCityInformationPortal.reposetry;

import com.smart.city.SmartCityInformationPortal.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Object> {

    User findByEmail(String username);

    void deleteByEmail(String userName);

}

package com.smart.city.SmartCityInformationPortal.services;

import Component.*;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.reposetry.CityRepository;
import com.smart.city.SmartCityInformationPortal.reposetry.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /// new user
    @Transactional
    public boolean saveNewUser(User newUser, String cityId){
        try {
            City city = cityRepository.findById(cityId);
            newUser.setPassword(PASSWORD_ENCODER.encode(newUser.getPassword()));
            newUser.setSuspend(false);
            newUser.setRoll(Arrays.asList("USER"));
            User saved = userRepository.save(newUser);
            city.getCityUsers().add(saved);
            cityRepository.save(city);
            return true;
        } catch (Exception e) {
            log.error("Exception in User Service",e);
            return false;
        }
    }

    /// check user
    public boolean saveUser(User user){
        User checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser.getEmail().equals(user.getEmail())){
            return true;
        }
        return false;
    }

    public DemoUser saveUser(UserLogin userLogin){
        DemoUser demoUser = null;
        User checkUser = userRepository.findByEmail(userLogin.getEmail());
        boolean matches = PASSWORD_ENCODER.matches(userLogin.getPassword(), checkUser.getPassword());
        if(checkUser.getEmail().equals(userLogin.getEmail()) && matches){
            demoUser.setName(checkUser.getName());
            demoUser.setPhone(checkUser.getPhone());
            demoUser.setCity(checkUser.getCity());
            demoUser.setEmail(checkUser.getEmail());
            demoUser.setAddress(checkUser.getAddress());
            return demoUser;
        }
        return demoUser;
    }

    /// update password
    public boolean updatePassword(PasswordReset passwordReset){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByEmail(Email);
        boolean existingPassword = PASSWORD_ENCODER.matches(passwordReset.getOldPassword(),existingUser.getPassword());
        String encodeNewPassword = PASSWORD_ENCODER.encode(passwordReset.getNewPassword());
        if(existingPassword){
            existingUser.setPassword(encodeNewPassword);
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    /// update email, address, and name
    public boolean updateUser(UpdateGmailOrUserName user, String Email) {
        boolean anyChange = false;

        User existingUser = userRepository.findByEmail(Email);
        if(existingUser.getEmail() == null || !existingUser.getEmail().equals(user.getEmail())){
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            anyChange = true;
        }
        if(!existingUser.getName().equals(user.getEmail())){
            User checkUser = userRepository.findByEmail(user.getEmail());
            if(checkUser == null){
                existingUser.setName(user.getEmail());
                userRepository.save(existingUser);
                anyChange = true;
            }
        }
        if(existingUser.getEmail() == null || !existingUser.getAddress().equals(user.getAddress())){
            User checkUser = userRepository.findByEmail(user.getEmail());
            if(checkUser == null){
                existingUser.setName(user.getEmail());
                userRepository.save(existingUser);
                anyChange = true;
            }
        }
        return anyChange;
    }

    /// delete user
    public boolean deleteUser(String password,String Email){
        User user = userRepository.findByEmail(Email);
        boolean userPassword = PASSWORD_ENCODER.matches(password,user.getPassword());
        if(userPassword){
            userRepository.deleteByEmail(Email);
            return true;
        }else{
            return false;
        }
    }

    public boolean updatePassword(PasswordReset passwordReset, String Email){
        User existingUser = userRepository.findByEmail(Email);
        boolean existingPassword = PASSWORD_ENCODER.matches(passwordReset.getOldPassword(),existingUser.getPassword());
        String encodeNewPassword = PASSWORD_ENCODER.encode(passwordReset.getNewPassword());
        if(existingPassword){
            existingUser.setPassword(encodeNewPassword);
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    /// get info of user
    public responceUser getInfo(String Email) {

        responceUser demoUser = new responceUser();
        User user = userRepository.findByEmail(Email);
        if(user != null) {
            demoUser.setName(user.getName());
            demoUser.setId(user.getId());
            demoUser.setEmail(user.getEmail());
            demoUser.setPhone(user.getPhone());
            demoUser.setAddress(user.getAddress());
            demoUser.setCity(user.getCity());
        }
        return demoUser;
    }


    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

}

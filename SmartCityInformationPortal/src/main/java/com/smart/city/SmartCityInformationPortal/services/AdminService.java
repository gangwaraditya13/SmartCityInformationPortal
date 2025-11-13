package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.entities.*;
import com.smart.city.SmartCityInformationPortal.reposetry.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UtilityRepository utilityRepository;


    @Transactional
    public boolean updateUserRoll(String email, String roll){
        User checkUser = userRepository.findByEmail(email);
        try{
            if (checkUser.getEmail().equals(email)) {
                List<String> collect = checkUser.getRoll().stream().filter(x -> x.equals(roll)).collect(Collectors.toList());
                if(collect.isEmpty())
                    checkUser.getRoll().add(roll);
                userRepository.save(checkUser);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            log.error("Exception in Admin Service {}", LocalDateTime.now(),e);
            return false;
        }
    }

    public boolean removeRoll(String email, String roll) {
        User checkUser = userRepository.findByEmail(email);
        boolean remove = false;
        try{
            if (checkUser.getEmail().equals(email)) {
                remove = checkUser.getRoll().removeIf(x -> x.equals(roll));
                if(remove) {
                    userRepository.save(checkUser);
                }
            }
            return remove;
        } catch (Exception e) {
            log.error("Exception in Admin Service {}", LocalDateTime.now(),e);
            return false;
        }
    }

    public boolean toSuspend(String emailAdmin,String email){
        User user = userRepository.findByEmail(email);
        User admin = userRepository.findByEmail(emailAdmin);

        List<String> cityAdmin = admin.getRoll().stream().filter(x -> x.toUpperCase().equals("ADMIN")).collect(Collectors.toList());

        if(cityAdmin != null){
            if (user != null){
                user.setSuspend(true);
                userRepository.save(user);
            }
        }
        return false;
    }

    public boolean toResume(String emailAdmin,String email){
        User user = userRepository.findByEmail(email);
        User admin = userRepository.findByEmail(emailAdmin);

        List<String> cityAdmin = admin.getRoll().stream().filter(x -> x.toUpperCase().equals("ADMIN")).collect(Collectors.toList());

        if(cityAdmin != null){
            if (user != null){
                user.setSuspend(false);
                userRepository.save(user);
            }
        }
        return false;
    }

    public List<City> getAllCity(){
        List<City> citys = cityRepository.findAll();
        return citys;
    }

    public List<Hospital> getAllHospital(){
        List<Hospital> allHospitals = hospitalRepository.findAll();
        return allHospitals;
    }

    public List<School> getAllSchool(){
        List<School> allSchool = schoolRepository.findAll();
        return allSchool;
    }

    public List<Utility> getAllUtility(){
        List<Utility> allUtility = utilityRepository.findAll();
        return allUtility;
    }

}

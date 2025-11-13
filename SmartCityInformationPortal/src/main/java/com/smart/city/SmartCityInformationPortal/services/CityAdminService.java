package com.smart.city.SmartCityInformationPortal.services;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.Complaint;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.reposetry.CityRepository;
import com.smart.city.SmartCityInformationPortal.reposetry.ComplaintRepository;
import com.smart.city.SmartCityInformationPortal.reposetry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityAdminService {
    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    public List<Complaint> pendingComplaint(String email){

        User user = userRepository.findByEmail(email);

        City city = cityRepository.findByCityName(user.getCity());

       List<Complaint> pendingComplaints = city.getCityComplaint().stream().filter(x -> x.getComplaintStatus().equals("PENDING")).collect(Collectors.toList());
       return pendingComplaints;
    }

    public List<Complaint> notPendingComplaint(String email){

        User user = userRepository.findByEmail(email);

        City city = cityRepository.findByCityName(user.getCity());

        List<Complaint> notPendingComplaints = city.getCityComplaint().stream().filter(x -> !x.getComplaintStatus().equals("PENDING")).collect(Collectors.toList());
        return notPendingComplaints;
    }

    public boolean toSuspend(String emailCityAdmin,String email){
        User user = userRepository.findByEmail(email);
        User userCityAdmin = userRepository.findByEmail(emailCityAdmin);

        List<String> cityAdmin = userCityAdmin.getRoll().stream().filter(x -> x.toUpperCase().equals("CITY_ADMIN")).collect(Collectors.toList());

        if(cityAdmin != null){
            if (user != null){
                user.setSuspend(true);
                userRepository.save(user);
            }
        }
        return false;
    }

    public boolean toResume(String emailCityAdmin,String email){
        User user = userRepository.findByEmail(email);
        User userCityAdmin = userRepository.findByEmail(emailCityAdmin);

        List<String> cityAdmin = userCityAdmin.getRoll().stream().filter(x -> x.toUpperCase().equals("CITY_ADMIN")).collect(Collectors.toList());

        if(cityAdmin != null){
            if (user != null){
                user.setSuspend(false);
                userRepository.save(user);
            }
        }
        return false;
    }

}

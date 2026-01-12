package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.dto.complaint.ComplaintDto;
import com.smart.city.SmartCityInformationPortal.dto.complaint.UpdateTitleOrDescription;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.Complaint;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.ComplaintRepository;
import com.smart.city.SmartCityInformationPortal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ComplaintService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public boolean createComplaint(ComplaintDto complaintDto, String email){

        try {
            User user = userRepository.findByEmail(email);
            City city = cityRepository.findByCityName(user.getCity()).orElseThrow();
            if(user != null) {
                Complaint complaint = modelMapper.map(complaintDto, Complaint.class);
                complaint.setComplaintStatus("PENDING");
                Complaint saved = complaintRepository.save(complaint);
                user.getComplaintList().add(saved);
                userRepository.save(user);
                city.getCityComplaint().add(saved);
                cityRepository.save(city);
                return true;
            }
        }catch (Exception e){
            log.error("Exception in Complaint Service",e);
            return false;
        }
        return false;
    }


    public boolean updateComplaint(UpdateTitleOrDescription complaint, String complaintId){
        boolean anyChange = false;
        try {
            Complaint findComplaint = complaintRepository.findById(complaintId);
            if(findComplaint != null) {
                if(findComplaint.getComplaintDescription() != complaint.getDescription()){
                    findComplaint.setComplaintDescription(complaint.getDescription());
                    complaintRepository.save(findComplaint);
                    anyChange = true;
                }
                if(findComplaint.getComplaintTitle() != complaint.getTitle()){
                    findComplaint.setComplaintTitle(complaint.getTitle());
                    complaintRepository.save(findComplaint);
                    anyChange = true;
                }
            }
        }catch (Exception e){
            log.error("Exception in Complaint Service",e);
            return anyChange;
        }
        return anyChange;
    }

    public boolean deleteComplaint(String complaintId){
        Complaint findComplaint = complaintRepository.findById(complaintId);
        if(findComplaint != null){
            complaintRepository.deleteById(complaintId);
            return true;
        }
        return false;
    }

    public Complaint getComplaint(String complaintId){
        Complaint findComplaint = complaintRepository.findById(complaintId);
        if(findComplaint != null){
            return findComplaint;
        }
        return findComplaint;
    }

}

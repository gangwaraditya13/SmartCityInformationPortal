package com.smart.city.SmartCityInformationPortal.dto.city;

import com.smart.city.SmartCityInformationPortal.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    ObjectId id;
    String cityName;
    List<User> cityUsers;
    List<School> citySchools;
    List<Hospital> cityHospitals;
    List<Utility> cityUtilities;
    List<Complaint> cityComplaint;
}

package com.smart.city.SmartCityInformationPortal.dto.user;

import com.smart.city.SmartCityInformationPortal.entities.Hospital;
import com.smart.city.SmartCityInformationPortal.entities.School;
import com.smart.city.SmartCityInformationPortal.entities.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseCityDto {
        String cityName;
        List<School> citySchools;
        List<Hospital> cityHospitals;
        List<Utility> cityUtilities;
}

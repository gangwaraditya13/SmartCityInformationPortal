package dto.utility;

import com.smart.city.SmartCityInformationPortal.selectDataEnum.UtilityDepartment;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilityUpdateDto {
    private String utilityId;
    private UtilityDepartment utilityDepartment; //Garbage, Road, Water, Electricity,e.t.c
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Indian mobile number"
    )
    private String utilityContact;
    private String utilityAddress;
    private  String utilityDepartmentOfficer;
}
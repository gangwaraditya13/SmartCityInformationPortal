package dto.school;

import com.smart.city.SmartCityInformationPortal.selectDataEnum.InstitutionCategory;
import com.smart.city.SmartCityInformationPortal.selectDataEnum.OwnershipType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {
    @NotBlank(message = "School name is required")
    private String schoolName;

    @NotNull(message = "Institution category is required")
    private InstitutionCategory category;   // SCHOOL / COLLEGE / UNIVERSITY

    private OwnershipType ownership;         // GOVERNMENT / PRIVATE / PRIVATE_AIDED

    @NotBlank(message = "School address is required")
    private String schoolAddress;

    @NotBlank(message = "Contact number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Indian mobile number"
    )
    private String schoolContact;
}


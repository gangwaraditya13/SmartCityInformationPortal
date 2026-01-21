package com.smart.city.SmartCityInformationPortal.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGmailOrUserName {
    private String name;
    private String email;
    private String address;
    @Pattern(
            regexp = "^(\\+91|0)?[6-9]\\d{9}$",
            message = "Invalid Indian mobile number"
    )
    private String phone;
}

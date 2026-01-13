package com.smart.city.SmartCityInformationPortal.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Invalid email address"
    )
    private String email;
    private String password;
    private String name;
    @Pattern(
            regexp = "^(\\+91|0)?[6-9]\\d{9}$",
            message = "Invalid Indian mobile number"
    )
    private String phone;
    @Pattern(
            regexp = "^[2-9]\\d{11}$",
            message = "Invalid Aadhaar number"
    )

    private String idProof;
    private String profilePhotoURL;
    private String profileProductId;
    private String address;
    private String city;
}

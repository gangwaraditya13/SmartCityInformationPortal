package com.smart.city.SmartCityInformationPortal.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTRequestTokenDto {
    private String jwtToken;
}

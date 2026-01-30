package com.smart.city.SmartCityInformationPortal.dto.city_updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityUpdatesUpdateImageDto {
    @NonNull
    private String id;
    @NonNull
    private String profilePhotoURL;
    @NonNull
    private String profileProductId;
}

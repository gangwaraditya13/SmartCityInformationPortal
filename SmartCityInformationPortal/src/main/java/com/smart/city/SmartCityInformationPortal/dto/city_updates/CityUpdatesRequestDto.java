package com.smart.city.SmartCityInformationPortal.dto.city_updates;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityUpdatesRequestDto {
    @NotNull
    private String title;
    @NonNull
    private String description;
    private String profilePhotoURL;
    private String profileProductId;
}

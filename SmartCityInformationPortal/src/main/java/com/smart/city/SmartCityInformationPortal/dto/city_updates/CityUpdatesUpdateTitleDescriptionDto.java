package com.smart.city.SmartCityInformationPortal.dto.city_updates;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityUpdatesUpdateTitleDescriptionDto {
    @NonNull
    private String id;
    @NotNull
    private String title;
    @NonNull
    private String description;
}

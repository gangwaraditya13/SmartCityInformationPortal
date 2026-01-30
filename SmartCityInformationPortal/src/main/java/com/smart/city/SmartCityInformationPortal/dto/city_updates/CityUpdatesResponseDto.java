package com.smart.city.SmartCityInformationPortal.dto.city_updates;

import com.smart.city.SmartCityInformationPortal.entities.CityUpdates;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityUpdatesResponseDto {
    private List<CityUpdates> cityUpdates;
}

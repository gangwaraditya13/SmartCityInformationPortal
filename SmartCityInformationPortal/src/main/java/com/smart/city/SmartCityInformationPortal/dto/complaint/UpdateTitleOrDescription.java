package com.smart.city.SmartCityInformationPortal.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTitleOrDescription {
    private String id;
    private String title;
    private String description;
}

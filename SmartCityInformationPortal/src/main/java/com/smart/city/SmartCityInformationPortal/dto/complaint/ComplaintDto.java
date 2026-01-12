package com.smart.city.SmartCityInformationPortal.dto.complaint;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {
    private String complaintToName;
    @NonNull
    private String complaintTitle;
    @NonNull
    private String complaintDescription;
    @NonNull
    private String complaintCategory; //e.g: Garbage, Road, Water, Electricity,e.t.c
}

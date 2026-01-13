package com.smart.city.SmartCityInformationPortal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.city.SmartCityInformationPortal.selectDataEnum.UtilityDepartment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "utility")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utility {
    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId id;
    @NonNull
    private UtilityDepartment utilityDepartment; //Garbage, Road, Water, Electricity,e.t.c
    @NonNull
    @Indexed(unique = true)
    private String utilityContact;
    @NonNull
    private String utilityAddress;
    @NonNull
    private  String utilityDepartmentOfficer;
}

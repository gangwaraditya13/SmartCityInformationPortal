package com.smart.city.SmartCityInformationPortal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.city.SmartCityInformationPortal.selectDataEnum.InstitutionCategory;
import com.smart.city.SmartCityInformationPortal.selectDataEnum.OwnershipType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "school")
@AllArgsConstructor
@NoArgsConstructor
public class School {
    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)
    private String schoolName;

    @NonNull
    private InstitutionCategory category;   // SCHOOL / COLLEGE / UNIVERSITY

    @NonNull
    private OwnershipType ownership;         // GOVERNMENT / PRIVATE

    @NonNull
    private String schoolAddress;

    @NonNull
    private String schoolContact;

}

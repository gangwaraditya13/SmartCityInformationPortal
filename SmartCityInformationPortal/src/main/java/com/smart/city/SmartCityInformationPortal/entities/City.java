package com.smart.city.SmartCityInformationPortal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "city")
public class City {
    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    ObjectId id;
    String cityName;
    @DBRef
    List<User> cityUsers = new ArrayList<>();
    @DBRef
    List<School> citySchools = new ArrayList<>();
    @DBRef
    List<Hospital> cityHospitals = new ArrayList<>();
    @DBRef
    List<Utility> cityUtilities = new ArrayList<>();
    @DBRef
    List<Complaint> cityComplaint = new ArrayList<>();
}

package com.smart.city.SmartCityInformationPortal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "city")
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @JsonProperty("_id")
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    ObjectId id;
    @Indexed(unique = true)
    String cityName;
    @DBRef
    List<User> cityUsers = new ArrayList<>();
    @DBRef
    List<School> citySchools= new ArrayList<>();
    @DBRef
    List<Hospital> cityHospitals= new ArrayList<>();
    @DBRef
    List<Utility> cityUtilities= new ArrayList<>();
    @DBRef
    List<Complaint> cityComplaint= new ArrayList<>();
}

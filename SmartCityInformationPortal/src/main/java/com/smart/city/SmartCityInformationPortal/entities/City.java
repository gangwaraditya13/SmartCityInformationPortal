package com.smart.city.SmartCityInformationPortal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "city")
public class City {
    @JsonProperty("_id")
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    ObjectId id;
    @Indexed(unique = true)
    String cityName;
    @DBRef
    List<User> cityUsers;
    @DBRef
    List<School> citySchools;
    @DBRef
    List<Hospital> cityHospitals;
    @DBRef
    List<Utility> cityUtilities;
    @DBRef
    List<Complaint> cityComplaint;
}

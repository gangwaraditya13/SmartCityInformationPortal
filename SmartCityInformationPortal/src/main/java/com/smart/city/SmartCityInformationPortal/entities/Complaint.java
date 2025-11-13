package com.smart.city.SmartCityInformationPortal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "complaint")
@Data
public class Complaint {
    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String complaintToName;
    @NonNull
    private String complaintTitle;
    @NonNull
    private String complaintDescription;
    @NonNull
    private String complaintCategory; //e.g: Garbage, Road, Water, Electricity,e.t.c
    private String complaintStatus;

}

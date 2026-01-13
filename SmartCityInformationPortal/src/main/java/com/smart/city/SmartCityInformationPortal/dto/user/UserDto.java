package com.smart.city.SmartCityInformationPortal.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String email;
    private String phone;
    private String idProof;
    private String profilePhotoURL;
    private String profileProductId;
    private String address;
    private String city;
    private boolean suspend;
    private List<String> roll = new ArrayList<>();
}

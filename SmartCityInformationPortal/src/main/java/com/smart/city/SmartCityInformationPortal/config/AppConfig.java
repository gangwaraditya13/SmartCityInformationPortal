package com.smart.city.SmartCityInformationPortal.config;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMongoAuditing
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Value("${CLOUD_NAME}")
    private String cloudName;
    @Value("${API_KEY}")
    private String apiKey;
    @Value("${API_SECRET}")
    private String apiSecret;

    @Bean
    public Cloudinary getCloudinary(){

        Map conifg = new HashMap();

        conifg.put("cloud_name",cloudName);
        conifg.put("api_key",apiKey);
        conifg.put("api_secret",apiSecret);
        conifg.put("secure",true);

        return new Cloudinary(conifg);
    }

}

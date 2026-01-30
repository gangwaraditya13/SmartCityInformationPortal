package com.smart.city.SmartCityInformationPortal.config;

import com.cloudinary.Cloudinary;
import com.smart.city.SmartCityInformationPortal.services.Impl.UserDetailServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMongoAuditing
public class AppConfig {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailServiceImp).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

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

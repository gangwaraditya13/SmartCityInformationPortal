package com.smart.city.SmartCityInformationPortal.services;

import com.smart.city.SmartCityInformationPortal.security.JWTUtil;
import com.smart.city.SmartCityInformationPortal.services.Impl.UserDetailServiceImp;
import com.smart.city.SmartCityInformationPortal.entities.City;
import com.smart.city.SmartCityInformationPortal.entities.User;
import com.smart.city.SmartCityInformationPortal.repository.CityRepository;
import com.smart.city.SmartCityInformationPortal.repository.UserRepository;
import dto.complaint.UpdateGmailOrUserName;
import dto.user.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    private JWTUtil jwtUtil;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /// new user
    @Transactional
    public boolean saveNewUser(UserRequestDto newUser){
        try {
            City city = cityRepository.findByCityName(newUser.getCity()).orElseThrow();
            User user = modelMapper.map(newUser, User.class);
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            user.setSuspend(false);
            user.setRoll(Arrays.asList("USER"));
            User saved = userRepository.save(user);
            city.getCityUsers().add(saved);
            cityRepository.save(city);
            return true;
        } catch (Exception e) {
            log.error("Exception in User Service",e);
            return false;
        }
    }

    /// check user exist
    public boolean checkUser(UserRequestDto user){
        User checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser == null){
            return true;
        }
        return false;
    }

    ///  here i use jwt token
    public TokenResponseDto loginToken(UserLoginRequestDto userLoginRequestDto){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.getEmail(),userLoginRequestDto.getPassword()));
            UserDetails userDetails = userDetailServiceImp.loadUserByUsername(userLoginRequestDto.getEmail());
            String jwtToken = jwtUtil.generateAccessToken(userDetails.getUsername());
            TokenResponseDto tokenResponseDto = new TokenResponseDto();
            tokenResponseDto.setJwtToken(jwtToken);
                return tokenResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /// update password
    public boolean updatePassword(PasswordResetDto passwordResetDto){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByEmail(Email);
        boolean existingPassword = PASSWORD_ENCODER.matches(passwordResetDto.getOldPassword(),existingUser.getPassword());
        String encodeNewPassword = PASSWORD_ENCODER.encode(passwordResetDto.getNewPassword());
        if(existingPassword){
            existingUser.setPassword(encodeNewPassword);
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    /// update email, address, and name
    public boolean updateUser(UpdateGmailOrUserName user, String Email) {
        boolean anyChange = false;

        User existingUser = userRepository.findByEmail(Email);
        if(existingUser.getEmail() == null || !existingUser.getEmail().equals(user.getEmail())){
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            anyChange = true;
        }
        if(!existingUser.getName().equals(user.getEmail())){
            User checkUser = userRepository.findByEmail(user.getEmail());
            if(checkUser == null){
                existingUser.setName(user.getEmail());
                userRepository.save(existingUser);
                anyChange = true;
            }
        }
        if(existingUser.getEmail() == null || !existingUser.getAddress().equals(user.getAddress())){
            User checkUser = userRepository.findByEmail(user.getEmail());
            if(checkUser == null){
                existingUser.setName(user.getEmail());
                userRepository.save(existingUser);
                anyChange = true;
            }
        }
        return anyChange;
    }

    /// delete user
    public boolean deleteUser(String password,String Email){
        User user = userRepository.findByEmail(Email);
        boolean userPassword = PASSWORD_ENCODER.matches(password,user.getPassword());
        if(userPassword){
            userRepository.deleteByEmail(Email);
            return true;
        }else{
            return false;
        }
    }


    /// get info of user
    public UserDto getInfoAdmin(String Email) {

        UserDto demoUser = new UserDto();
        User user = userRepository.findByEmail(Email);
        userRepository.findAll();
        if(user != null) {
            demoUser.setName(user.getName());
            demoUser.setId(user.getId());
            demoUser.setEmail(user.getEmail());
            demoUser.setPhone(user.getPhone());
            demoUser.setAddress(user.getAddress());
            demoUser.setCity(user.getCity());
        }
        return demoUser;
    }

    /// get info of user
    public UserDto getInfo(String Email) {
        User user = userRepository.findByEmail(Email);
        if(user != null) {
           return modelMapper.map(user, UserDto.class);
        }
        return null;
    }



    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

}

package com.project.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.project.cafe.JWT.CustomerUsersDetailsService;
import com.project.cafe.JWT.JWTFilter;
import com.project.cafe.JWT.JWTUtil;
import com.project.cafe.constants.CafeConstants;
import com.project.cafe.dao.UserDao;
import com.project.cafe.pojo.User;
import com.project.cafe.service.UserService;
import com.project.cafe.util.CafeUtils;
import com.project.cafe.util.EmailUtils;
import com.project.cafe.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service //Business Logic
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    JWTFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        log.info("Inside Signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                //check email id is in database or not
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap)); //persisting the data into the database
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email Already Exists.", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        else {
            return false;
        }
    }
    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
         log.info("Inside Login");
         try {
             log.info("Inside Login try block");
             Authentication auth = authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
             );

             if(auth.isAuthenticated()){
                 if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                     return new ResponseEntity<String>("{\"token\":\"" +
                             jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                     customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                     HttpStatus.OK);
                 }
                 else {
                     return  new ResponseEntity<String>("{\"message\":\"" + "wait for admin approval." + "\"}",
                             HttpStatus.BAD_REQUEST);
                 }
             }
         }catch (Exception e){
             log.error("{}", e);
         }
        return  new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials" + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);

            }
            else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new  ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){


                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                    return CafeUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
                }
                else{
                    return CafeUtils.getResponseEntity("User Id does not Exists", HttpStatus.OK);
                }

            }
            else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Already implemented JWT token | If hitting the API without valid token it would return 403 Exception | True if hit with proper Token
    //Validate the user basically through TOKEN
    @Override
    public ResponseEntity<String> checkToken() {
      return CafeUtils.getResponseEntity("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!userObj.equals(null)){
                if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return CafeUtils.getResponseEntity("Password Updated Successfully",HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            User userObj = userDao.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(userObj) && !Strings.isNullOrEmpty(userObj.getEmail())){
                emailUtils.forgotMail(userObj.getEmail(),"Credentials by Cafe Management System", userObj.getPassword());
            }
            return CafeUtils.getResponseEntity("Check Your Mail for Credentials",HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {

        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendMessage(jwtFilter.getCurrentUser(),"Account Approved!","USER:-" +user+"\n is Approved by \nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);
        }else {
            emailUtils.sendMessage(jwtFilter.getCurrentUser(),"Account Disabled!","USER:-" +user+"\n is Disabled by \nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);

        }
    }
}

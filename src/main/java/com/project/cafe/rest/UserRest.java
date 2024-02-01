package com.project.cafe.rest;

import com.project.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user") //Class level;
public interface UserRest {
 //The 2 API down, are open API, dont need JWT Token to Access these api's -signup and login
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping("/getAll")
    public ResponseEntity<List<UserWrapper>> getAllUser();

    @PostMapping("/updateUser")
   public ResponseEntity<String> updateUser(@RequestBody(required = true)Map<String, String> requestMap);

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/forgotPassword") //Bypassed the API in SecurityConfig so that we dont need to login to call the API
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);


}

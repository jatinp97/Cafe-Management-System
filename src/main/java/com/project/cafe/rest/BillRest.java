package com.project.cafe.rest;

import com.project.cafe.pojo.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/bill")
public interface BillRest {

    @PostMapping("/generateBill")
    ResponseEntity<String> generateBill(@RequestBody Map<String, Object> requestMap);
    //Map<String, Object> as we are expecting a List of JSON array of type Object

    @GetMapping("/getBills")
    ResponseEntity<List<Bill>> getBills();

    @PostMapping("/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestMap);

    @PostMapping("/deleteBill/{id}")
    ResponseEntity<String> deleteBill(@PathVariable Integer id);



}

package com.project.cafe.rest;

import com.project.cafe.pojo.Category;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
public interface CategoryRest {

    @PostMapping("/addCategory")
    ResponseEntity<String> addNewCategory(@RequestBody(required = true)
                                          Map<String, String> requestMap);

    @GetMapping("/getCategory")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false)
                                                  String filterValue);


    @PostMapping("updateCategory")
    ResponseEntity<String> updateCategory(@RequestBody(required = true)
                                          Map<String, String> requestMap);
}

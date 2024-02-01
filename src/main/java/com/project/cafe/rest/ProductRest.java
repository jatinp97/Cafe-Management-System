package com.project.cafe.rest;

import com.project.cafe.pojo.Product;
import com.project.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping("/addProduct")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String,String> requestMap);

    @GetMapping("/getAllProducts")
    ResponseEntity<List<ProductWrapper>> getAllProducts();

    @PostMapping("/updateProduct")
    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

    @PostMapping("/deleteProduct/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PostMapping("/updateProductStatus")
    ResponseEntity<String> updateProductStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping("/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

    @GetMapping("/getById/{id}")
    ResponseEntity<ProductWrapper> getProductById(@PathVariable Integer id);


}

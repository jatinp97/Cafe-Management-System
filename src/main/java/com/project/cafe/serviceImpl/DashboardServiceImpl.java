package com.project.cafe.serviceImpl;

import com.project.cafe.dao.BillDao;
import com.project.cafe.dao.CategoryDao;
import com.project.cafe.dao.ProductDao;
import com.project.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    BillDao billDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;


    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
        Map<String, Object> map = new HashMap<>();
        map.put("category",categoryDao.count());
        map.put("product", productDao.count());
        map.put("bill", billDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

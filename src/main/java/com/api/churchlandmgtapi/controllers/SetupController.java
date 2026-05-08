package com.api.churchlandmgtapi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.churchlandmgtapi.config.DBConnection;
import com.api.churchlandmgtapi.services.setup.SetupService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/setup_service")
@Tag(name = "Setup Service", description = "Setup Service for TerraFinder Application")
public class SetupController {
    SetupService setupService = new SetupService();

    @Autowired
    private DBConnection dbConnection;

    @GetMapping("/get_districts_list")
    public ResponseEntity<?> getDistrictsList() throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.getDistrictsList();
        setupService.con.close();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get_congregations_list")
    public ResponseEntity<?> getCongregationsList() throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.getCongregationsList();
        setupService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_districts_by_presbytery")
    public ResponseEntity<?> getDistrictList(@RequestBody String jsonReq)  throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.getDistrictsByPresbytery(jsonReq);
        setupService.con.close();
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/get_congregations_by_district")
    public ResponseEntity<?> getCongregationList(@RequestBody String jsonReq)  throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.getCongregationsByDistrict(jsonReq);
        setupService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add_district")
    public ResponseEntity<?> addDistrict(@RequestBody String jsonReq)  throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.addDistrict(jsonReq);
        setupService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add_congregation")
    public ResponseEntity<?> addCongregation(@RequestBody String jsonReq)  throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.addCongregation(jsonReq);
        setupService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update_district")
    public ResponseEntity<?> updateDistrict(@RequestBody String jsonReq)  throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.updateDistrict(jsonReq);
        setupService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update_congregation")
    public ResponseEntity<?> updateCongregation(@RequestBody String jsonReq)  throws Exception {
        setupService.con = dbConnection.getConnection();
        String result = setupService.updateCongregation(jsonReq);
        setupService.con.close();
        return ResponseEntity.ok(result);
    }
}

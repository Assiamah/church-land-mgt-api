package com.api.churchlandmgtapi.controllers;

import java.sql.Connection;
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
    // Now safe to share: SetupService holds no state of its own - every method
    // takes its Connection as a parameter, so there is nothing left for two
    // concurrent requests to collide on.
    SetupService setupService = new SetupService();

    @Autowired
    private DBConnection dbConnection;

    @GetMapping("/get_districts_list")
    public ResponseEntity<?> getDistrictsList() throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.getDistrictsList(conn);
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/get_congregations_list")
    public ResponseEntity<?> getCongregationsList() throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.getCongregationsList(conn);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/get_districts_by_presbytery")
    public ResponseEntity<?> getDistrictList(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.getDistrictsByPresbytery(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/get_congregations_by_district")
    public ResponseEntity<?> getCongregationList(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.getCongregationsByDistrict(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/add_district")
    public ResponseEntity<?> addDistrict(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.addDistrict(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/select_district")
    public ResponseEntity<?> selectDistrict(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.selectDistrict(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/select_congregation")
    public ResponseEntity<?> selectCongregation(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.selectCongregation(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/add_congregation")
    public ResponseEntity<?> addCongregation(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.addCongregation(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/update_district")
    public ResponseEntity<?> updateDistrict(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.updateDistrict(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/update_congregation")
    public ResponseEntity<?> updateCongregation(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.updateCongregation(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/add_designation")
    public ResponseEntity<?> addDesignation(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.addDesignation(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/get_designations")
    public ResponseEntity<?> getDesignations(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.getDesignations(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/update_designation")
    public ResponseEntity<?> updateDesignation(@RequestBody String jsonReq) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String result = setupService.updateDesignation(conn, jsonReq);
            return ResponseEntity.ok(result);
        }
    }
}
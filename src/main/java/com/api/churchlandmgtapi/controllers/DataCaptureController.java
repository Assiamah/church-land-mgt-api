package com.api.churchlandmgtapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.churchlandmgtapi.services.dataCapture.DataCaptureService;
import com.api.churchlandmgtapi.config.DBConnection;

@RestController
@RequestMapping("/v1/data_service")
@Tag(name = "Data Service", description = "Data Service for Church Land Mgt Application")
public class DataCaptureController {

    DataCaptureService dataCaptureService = new DataCaptureService();

    @Autowired
    private DBConnection dbConnection;
    
    @PostMapping("/save_data_capture")
    public ResponseEntity<?> saveDataCapture(@RequestBody String jsonReq)  throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.saveDataCapture(jsonReq);
        dataCaptureService.con.close();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get_all_data_captures")
    public ResponseEntity<?> getAllDataCaptures()  throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.getAllDataCaptures();
        dataCaptureService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_data_capture_by_id")
    public ResponseEntity<?> getDataCaptureById(@RequestBody String jsonReq)  throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.getDataCaptureById(jsonReq);
        dataCaptureService.con.close();
        return ResponseEntity.ok(result);
    }
}

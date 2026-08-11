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

    @PostMapping("/save_coordinate_upload")
    public ResponseEntity<?> saveCoordinateUpload(@RequestBody String jsonReq)  throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.saveCoordinateUpload(jsonReq);
        dataCaptureService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_coordinate_uploads_by_congregation")
    public ResponseEntity<?> getCoordinateUploadsByCongregation(@RequestBody String jsonReq)  throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.getCoordinateUploadsByCongregation(jsonReq);
        dataCaptureService.con.close();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_quality_control_queue")
    public ResponseEntity<?> getQualityControlQueue(@RequestBody String jsonReq) throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.getQualityControlQueue(jsonReq);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_quality_control_record")
    public ResponseEntity<?> getQualityControlRecord(@RequestBody String jsonReq) throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.getQualityControlRecord(jsonReq);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit_quality_control_review")
    public ResponseEntity<?> submitQualityControlReview(@RequestBody String jsonReq) throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.submitQualityControlReview(jsonReq);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update_quality_control_entry")
    public ResponseEntity<?> updateQualityControlEntry(@RequestBody String jsonReq) throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.updateQualityControlEntry(jsonReq);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_returned_quality_control_entries")
    public ResponseEntity<?> getReturnedQualityControlEntries(@RequestBody String jsonReq) throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.getReturnedQualityControlEntries(jsonReq);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_data_management_records")
    public ResponseEntity<?> getDataManagementRecords(@RequestBody String jsonReq) throws Exception {
        dataCaptureService.con = dbConnection.getConnection();
        String result = dataCaptureService.getDataManagementRecords(jsonReq);
        return ResponseEntity.ok(result);
    }
}

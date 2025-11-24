package com.api.churchlandmgtapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.churchlandmgtapi.services.documents.DocumentService;
import com.api.churchlandmgtapi.config.DBConnection;

@RestController
@RequestMapping("/v1/document_service")
@Tag(name = "Document Service", description = "Document Service for Church Land Mgt Application")
public class DocumentController {
    
    DocumentService documentService = new DocumentService();

    @Autowired
    private DBConnection dbConnection;
    
    @PostMapping("/save_data_capture_documents")
    public ResponseEntity<?> saveDataCaptureDocuments(@RequestBody String jsonReq) throws Exception {
        documentService.con = dbConnection.getConnection();
        String result = documentService.saveDataCaptureDocuments(jsonReq);
        documentService.con.close();

        return ResponseEntity.ok(new JSONObject(result).toMap());
    }
}

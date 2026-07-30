package com.api.churchlandmgtapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.churchlandmgtapi.config.DBConnection;
import com.api.churchlandmgtapi.services.reports.ReportService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/report_service")
@Tag(name = "Report Service", description = "Reporting Service for Church Land Mgt Application")
public class ReportController {

    ReportService reportService = new ReportService();

    private final DBConnection dbConnection;

    ReportController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @PostMapping("/get_land_report_dashboard")
    public ResponseEntity<?> getLandReportDashboard(@RequestBody String jsonReq) throws Exception {
        reportService.con = dbConnection.getConnection();
        String result = reportService.getLandReportDashboard(jsonReq);
        reportService.con.close();
        return ResponseEntity.ok(result);
    }
}

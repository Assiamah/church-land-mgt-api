package com.api.churchlandmgtapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.churchlandmgtapi.config.DBConnection;
import com.api.churchlandmgtapi.services.maps.MapService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/map_service")
@Tag(name = "Map Service", description = "Map Service for Church Land Mgt Application")
public class MapController {

    MapService mapService = new MapService();

    @Autowired
    private DBConnection dbConnection;

    @PostMapping("/get_land_polygons")
    public ResponseEntity<?> getLandPolygons(@RequestBody String jsonReq) throws Exception {
        mapService.con = dbConnection.getConnection();
        String result = mapService.getLandPolygons(jsonReq);
        mapService.con.close();
        return ResponseEntity.ok(result);
    }
}

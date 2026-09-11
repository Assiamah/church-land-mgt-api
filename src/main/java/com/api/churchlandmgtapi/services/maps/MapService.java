package com.api.churchlandmgtapi.services.maps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONObject;

public class MapService {

    public Connection con = null;

    public String getPrebyLandCentroidsGeoJson() throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String sql = "SELECT maps.get_preby_land_centroids_geojson() AS result";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                result = rs.getString("result");
            }
        } catch (SQLException e) {
            System.out.println("Error loading land centroids GeoJSON: " + e.getMessage());

            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Database error: " + e.getMessage());
            result = errorResponse.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result;
    }

    public String getLandPolygons(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String sql = "SELECT maps.get_land_polygons(?::jsonb) AS result";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, jsonReq);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getString("result");
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error loading land polygons: " + e.getMessage());

            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Database error: " + e.getMessage());
            result = errorResponse.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result;
    }
}

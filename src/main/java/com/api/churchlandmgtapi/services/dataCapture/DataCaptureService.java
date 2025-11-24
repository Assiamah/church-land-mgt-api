package com.api.churchlandmgtapi.services.dataCapture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONObject;

public class DataCaptureService {

    public Connection con = null;

    public String saveDataCapture(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String SQL = "SELECT maps.save_data_capture(?::jsonb) AS result"; // 👈 add alias

        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve the JSON result
                String jsonResult = rs.getString("result"); // 👈 use the alias
                result = jsonResult; // already JSON
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error saving data capture: " + e.getMessage());

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

    public String getAllDataCaptures() throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String SQL = "SELECT maps.get_all_data_captures() AS result"; // 👈 add alias

        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve the JSON result
                String jsonResult = rs.getString("result"); // 👈 use the alias
                result = jsonResult; // already JSON
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error getting all data captures: " + e.getMessage());

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
                    // Handle the exception if needed
                }
            }
        }
        return result;
    }
        
    public String getDataCaptureById(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String SQL = "SELECT maps.get_data_capture_by_id(?::jsonb) AS result"; // 👈 add alias

        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve the JSON result
                String jsonResult = rs.getString("result"); // 👈 use the alias
                result = jsonResult; // already JSON
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error getting data capture by id: " + e.getMessage());

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
                    // Handle the exception if needed
                }
            }
        }
        
        System.out.println(result);
        return result;
    }
    
    
}

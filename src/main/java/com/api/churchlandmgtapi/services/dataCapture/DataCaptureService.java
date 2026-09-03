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
        String SQL = "SELECT maps.save_data_capture_for_quality_control(?::jsonb) AS result";

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
        
        return result;
    }




        public String getCaptureByCongregationName(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String SQL = "SELECT maps.get_data_capture_by_congregation_name(?::jsonb) AS result"; // 👈 add alias

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
            System.out.println("Error getting data capture by congregation name: " + e.getMessage());

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


    public String saveCoordinateUpload(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT maps.save_coordinate_upload(?::jsonb) AS result"; // 👈 add alias
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
            System.out.println("Error saving coordinate upload: " + e.getMessage());

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
    
    public String getCoordinateUploadsByCongregation(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT maps.get_coordinate_uploads_by_congregation(?::jsonb) AS result"; // 👈 add alias
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
            System.out.println("Error getting coordinate uploads by congregation: " + e.getMessage());

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

    public String getQualityControlQueue(String jsonReq) throws Exception {
        return executeJsonFunction("maps.get_quality_control_queue", jsonReq);
    }

    public String getQualityControlRecord(String jsonReq) throws Exception {
        return executeJsonFunction("maps.get_quality_control_record", jsonReq);
    }

    public String submitQualityControlReview(String jsonReq) throws Exception {
        return executeJsonFunction("maps.submit_quality_control_review", jsonReq);
    }

    public String updateQualityControlEntry(String jsonReq) throws Exception {
        return executeJsonFunction("maps.update_quality_control_entry", jsonReq);
    }

    public String getReturnedQualityControlEntries(String jsonReq) throws Exception {
        return executeJsonFunction("maps.get_returned_quality_control_entries", jsonReq);
    }

    public String getDataManagementRecords(String jsonReq) throws Exception {
        return executeJsonFunction("maps.get_data_management_records", jsonReq);
    }

    private String executeJsonFunction(String functionName, String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String sql = "SELECT " + functionName + "(?::jsonb) AS result";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("result");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing " + functionName + ": " + e.getMessage());
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

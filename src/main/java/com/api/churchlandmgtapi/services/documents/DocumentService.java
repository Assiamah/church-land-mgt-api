package com.api.churchlandmgtapi.services.documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONObject;

public class DocumentService {
    
    public Connection con = null;

    public String saveDataCaptureDocuments(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String SQL = "SELECT documents.save_data_capture_documents(?::jsonb) AS result";

        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getString("result");
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error saving data capture documents: " + e.getMessage());
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Database error: " + e.getMessage());
            result = errorResponse.toString();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        System.out.println("DB Response: " + result);
        return result;
    }

     public String getUploadedDocumentCount(String jsonReq) throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }

        String result = null;
        String SQL = "SELECT documents.get_uploaded_document_count(?::jsonb) AS result";

        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getString("result");
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error getting uploaded document count: " + e.getMessage());
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Database error: " + e.getMessage());
            result = errorResponse.toString();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        System.out.println("DB Response: " + result);
        return result;
    }

    public String getDocumentCount() throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM documents.get_document_count()";
		Connection conn = con;
		try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("get_document_count");
			}
			rs.close();
		} catch (SQLException e) {
			// Print Errors in console.
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
    }

    public String getUploadedDocuments() throws Exception {
        if (con == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM documents.get_uploaded_documents()";
		Connection conn = con;
		try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("get_uploaded_documents");
			}
			rs.close();
		} catch (SQLException e) {
			// Print Errors in console.
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
        
		return result;
    }
}

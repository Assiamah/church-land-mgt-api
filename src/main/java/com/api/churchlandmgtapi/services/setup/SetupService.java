package com.api.churchlandmgtapi.services.setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetupService {

    // NOTE: the previous shared "public Connection con" field has been removed.
    // It was a mutable field on a (singleton-scoped) service bean, so two
    // concurrent requests could overwrite each other's connection and one
    // request's `finally { conn.close(); }` would close a connection the OTHER
    // request was still reading from - producing exactly the
    // "This statement has been closed" error seen in production. Every method
    // below now takes its Connection as a parameter instead, so each request
    // uses a connection that is entirely local to that call.

    public String getDistrictsList(Connection conn) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.get_districts_list()";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                result = rs.getString("get_districts_list");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String getCongregationsList(Connection conn) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.get_congregations_list()";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                result = rs.getString("get_congregations_list");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String getDistrictsByPresbytery(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.get_districts_by_presbytery(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("get_districts_by_presbytery");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String getCongregationsByDistrict(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.get_congregations_by_district(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("get_congregations_by_district");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String addDistrict(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.insert_district(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("insert_district");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String selectDistrict(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.select_district(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("select_district");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String selectCongregation(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.select_congregation(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("select_congregation");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String addCongregation(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.insert_congregation(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("insert_congregation");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String updateDistrict(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.update_district(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("update_district");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String updateCongregation(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.update_congregation(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("update_congregation");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String addDesignation(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.add_designation(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("add_designation");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String getDesignations(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.get_designations(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("get_designations");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }

    public String updateDesignation(Connection conn, String jsonReq) throws Exception {
        if (conn == null) {
            throw new Exception("Database connection is not established");
        }
        String result = null;
        String SQL = "SELECT * FROM system.update_designation(?::json)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, jsonReq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString("update_designation");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }
}
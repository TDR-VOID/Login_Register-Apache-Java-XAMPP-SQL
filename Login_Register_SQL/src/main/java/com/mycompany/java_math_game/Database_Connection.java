/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_math_game;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Database_Connection {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/login_app";
    static final String USER = "root"; // Change this if you have set a different username
    static final String PASS = ""; // Change this if you have set a password for your MySQL root user
    private static boolean databaseConnectionError = false;

    public static boolean validateLogin(String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        boolean isValid = false;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet rs = stmt.executeQuery(sql);
            isValid = rs.next();
            rs.close();
            //stmt.close();
            //conn.close();
        } catch (SQLException se) {
            //se.printStackTrace();
            databaseConnectionError = true;
            JOptionPane.showMessageDialog(null, "Database coneection error: "+ se.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return isValid;
    }
    
    
        public static boolean isDatabaseConnectionError() {
        return databaseConnectionError;
    }
        
     public static boolean registerUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return isSuccess;
    }       
        
        
        
     
    
}

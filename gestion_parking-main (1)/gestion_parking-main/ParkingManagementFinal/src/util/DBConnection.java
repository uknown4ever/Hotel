/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author PC
 */


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/parking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {

        Connection conn = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connexion réussie");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return conn;

    }

}


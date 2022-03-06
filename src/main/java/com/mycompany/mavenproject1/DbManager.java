/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class DbManager {
    public static Connection connection;
    public static PreparedStatement ps;
    public static Statement statement;
    public static ResultSet resultSet;
    private static final String url = "jdbc:postgresql://localhost:5432/souvenirs";
    private static final String username = "author";
    private static final String pass = "lkj";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection(url, username, pass);
        return connection;
    }

    public static void createTables() {    // did statement close? 
        try {
            connection = getConnection();
            ps = connection.prepareStatement(
                    "CREATE TABLE  IF NOT EXISTS manufacturer(" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(100) NOT NULL, " +
                    "country VARCHAR(255)" +
                    ");");
            ps.executeUpdate();
            ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS souvenirs(" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(100) NOT NULL, " +
                    "manufacturer_data VARCHAR(255), " +
                    "release_date varchar(100), " +
                    "price INT NOT NULL, " +
                    "manufacturer_id INT," +
                    "FOREIGN KEY (manufacturer_id) references manufacturer (id)" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void postTables() {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.addBatch("INSERT INTO manufacturer (title, country) VALUES ('GARNIER', 'France')");
            statement.addBatch("INSERT INTO manufacturer (title, country) VALUES ('SCHWARZKOPF', 'Germany')");
            statement.addBatch("INSERT INTO manufacturer (title, country) VALUES ('NIVEA', 'Germany')");
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

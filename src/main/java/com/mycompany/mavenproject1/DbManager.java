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
import java.util.LinkedList;
import java.util.List;

public class DbManager {
    private static PreparedStatement ps;
    private static Statement statement;
    private static ResultSet resultSet;
    private static final String url = "jdbc:postgresql://localhost:5432/souvenirs";
    private static final String username = "author";
    private static final String pass = "lkj";
    
    static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    static void createTables(Connection connection) {
        try {
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
        } finally {
            closePrepareStatement(ps);
        }
    }
    
    static void postTablesTestData(Connection connection) {
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.addBatch("INSERT INTO manufacturer (title, country) VALUES ('GARNIER', 'France');");
            statement.addBatch("INSERT INTO manufacturer (title, country) VALUES ('SCHWARZKOPF', 'Germany');");
            statement.addBatch("INSERT INTO manufacturer (title, country) VALUES ('Nivea', 'Germany');");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Shampoo', 'some address', '03.22', 50, 1)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Shampoo', 'some address', '07.21', 60, 3)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Soap', 'some address', '03.22', 25, 2)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Soap', 'some address', '01.22', 20, 1)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Sopa', 'some address', '02.22', 20, 2)");
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            closeStatement(statement);
            if(connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void closeStatement(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
             } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void closePrepareStatement(PreparedStatement ps) {
        if(ps != null) {
            try {
                ps.close();
             } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    static void closeConnection(Connection connection) {if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    static List<Souvenirs> getSouvenirsListByCountry(String country, Connection connection) {
        String requestSouvenirsListByCountry = "select souvenirs.* from souvenirs inner join manufacturer on "
                + "manufacturer.id = souvenirs.manufacturer_id where manufacturer.country = ?;";
        List<Souvenirs> lst = new LinkedList<>();
        try {
            ps = connection.prepareStatement(requestSouvenirsListByCountry);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lst.add(new Souvenirs(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return lst;
    }

    static List<Souvenirs> getSouvenirsListByManufacturer(String data) {
        return null;
    }
    
    
    
}

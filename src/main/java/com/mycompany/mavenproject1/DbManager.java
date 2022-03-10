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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class DbManager {
    private static PreparedStatement prepStatement;
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
            prepStatement = connection.prepareStatement(
                    "CREATE TABLE  IF NOT EXISTS manufacturer(" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(100) NOT NULL, " +
                    "country VARCHAR(255)" +
                    ");");
            prepStatement.executeUpdate();
            prepStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS souvenirs(" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(100) NOT NULL, " +
                    "manufacturer_data VARCHAR(255), " +
                    "release_date DATE, " +
                    "price INT NOT NULL, " +
                    "manufacturer_id INT," +
                    "FOREIGN KEY (manufacturer_id) references manufacturer (id) ON DELETE CASCADE" +
                    ");");
            prepStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(prepStatement);
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
                    + "VALUES ('Shampoo', 'some address', '2021.09.22', 50, 1)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Shampoo', 'some address', '2021.09.22', 60, 3)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Soap', 'some address', '2022.03.22', 25, 2)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Soap', 'some address', '2022.03.22', 20, 1)");
            statement.addBatch("INSERT INTO souvenirs (title, manufacturer_data, release_date, price, manufacturer_id)"
                    + "VALUES ('Sopa', 'some address', '2022.03.22', 20, 2)");
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

    
    static List<Souvenirs> getSouvenirsListByCountry(String country, Connection connection) {
        String requestSouvenirsListByCountry = "select souvenirs.* from souvenirs inner join manufacturer on "
                + "manufacturer.id = souvenirs.manufacturer_id where manufacturer.country = ?;";
        List<Souvenirs> souvenirsList = new LinkedList<>();
        try {
            prepStatement = connection.prepareStatement(requestSouvenirsListByCountry);
            prepStatement.setString(1, country);
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                souvenirsList.add(new Souvenirs(
                        resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(prepStatement);
        }
        return souvenirsList;
    }

    static List<Souvenirs> getSouvenirsListByManufacturer(String manufacturerTitle, Connection connection) {
        String requestSouvenirsListByCountry = "select souvenirs.* from souvenirs inner join manufacturer on "
                + "manufacturer.id = souvenirs.manufacturer_id where manufacturer.title = ?;";
        List<Souvenirs> souvenirsList = new LinkedList<>();
        try {
            prepStatement = connection.prepareStatement(requestSouvenirsListByCountry);
            prepStatement.setString(1, manufacturerTitle);
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                souvenirsList.add(new Souvenirs(
                        resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(prepStatement);
        }
        return souvenirsList;
    }

    static List<Manufacturer> getManufacturersListBySouvenirsPraceLessThan(int price, Connection connection) {
        String s = "select manufacturer.* from manufacturer inner join souvenirs on "
                + "souvenirs.manufacturer_id = manufacturer.id where souvenirs.price <= ?;";
        List<Manufacturer> manufacturersListByPrace = new LinkedList<>();
        try {
            prepStatement = connection.prepareStatement(s);
            prepStatement.setInt(1, price);
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                manufacturersListByPrace.add(new Manufacturer(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(prepStatement);
        }
        return manufacturersListByPrace;
    }

    static List<Souvenirs> getManufacturersListByProducedYear(int year, Connection connection) {
        String ss = "SELECT * FROM souvenirs WHERE EXTRACT(YEAR FROM release_date) = ?;";
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<Souvenirs> manufacturersListByProducedYear = new LinkedList<>();
        try {
            prepStatement = connection.prepareStatement(ss);
            prepStatement.setInt(1, year);
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                manufacturersListByProducedYear.add(
                        new Souvenirs(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), 
                        df.format(resultSet.getDate(4)), resultSet.getInt(5), resultSet.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(prepStatement);
        }
        return manufacturersListByProducedYear;
    }

    static void deleteManufacturerAndSouvenirs(String title, Connection connection) {
        String requestForDelete = "DELETE FROM manufacturer WHERE country = ?;";
        try {
            prepStatement = connection.prepareStatement(requestForDelete);
            prepStatement.setString(1, title);
            prepStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(prepStatement);
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
}

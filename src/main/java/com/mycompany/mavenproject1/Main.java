/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.util.List;


public class Main {
        
    public static void main(String[] args) {
        Connection connection = DbManager.getConnection();
        // DbManager.createTables(connection);
        // DbManager.postTablesTestData(connection);
        String s1 = "SELECT souvenirs.title, manufacturer.title FROM "
                + "manufacturer INNER JOIN souvenirs ON manufacturer.id = souvenirs.manufacturer_id";
        String s = "select souvenirs.* from souvenirs inner join manufacturer on manufacturer.id = souvenirs.manufacturer_id where manufacturer.country = 'France';";
        // Вывести информацию о сувенирах, произведенных в заданной стране.
//        List<Souvenirs> list = DbManager.getSouvenirsListByManufacturer("data");
        
        
        List<Souvenirs> lists = DbManager.getSouvenirsListByCountry("France", connection);
        System.out.println(lists.toString());
        DbManager.closeConnection(connection);
    }
}

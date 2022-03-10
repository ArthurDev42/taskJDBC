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
        
        DbManager.createTables(connection);
        DbManager.postTablesTestData(connection);
        
//        Вывести информацию о сувенирах заданного производителя.
        List<Souvenirs> result1 = DbManager.getSouvenirsListByManufacturer("Nivea", connection);
        System.out.println(result1.toString());
        
//        Вывести информацию о сувенирах, произведенных в заданной стране.
        List<Souvenirs> result2 = DbManager.getSouvenirsListByCountry("France", connection);
        System.out.println(result2.toString());

//        Вывести информацию о производителях, чьи цены на сувениры меньше заданной.
        List<Manufacturer> result3 = DbManager.getManufacturersListBySouvenirsPraceLessThan(30, connection);
        System.out.println(result3.toString());
        
//        Вывести информацию о производителях заданного сувенира, произведенного в заданном году.
        List<Souvenirs> result4 = DbManager.getManufacturersListByProducedYear(2021, connection);
        System.out.println(result4.toString());

//        Удалить заданного производителя и его сувениры.
        DbManager.deleteManufacturerAndSouvenirs("France", connection);
        
        DbManager.closeConnection(connection);
    }
}

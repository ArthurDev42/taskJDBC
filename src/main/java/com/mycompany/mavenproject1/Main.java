/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class Main {
        
    public static void main(String[] args) {
        
        try {
            DbManager.createTables();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (Connection connection = DbManager.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM ALL;";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Object> listik = new ArrayList();
            while (resultSet.next()) {
                
                int id = resultSet.getInt(1);
                if(id == 1) {
                   resultSet.updateInt("1", "2"); 
                   resultSet.updateBow();
                }
                
                String s = resultSet.getString(2);
                listik.add(new Object(id, s));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
}

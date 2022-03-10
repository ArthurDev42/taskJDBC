/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private int id;
    private String title;
    private String country;

    Manufacturer(int id, String title, String country) {
        this.id = id;
        this.title = title;
        this.country = country;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String name) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                '}' + "\n";
    }
}

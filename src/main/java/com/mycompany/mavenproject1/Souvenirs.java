/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.io.Serializable;

public class Souvenirs  implements Serializable {
    private int id;
    private String title;
    private String manufacturer_data;
    private String release_date;
    private int price;
    private int manufacturer_id;

    Souvenirs(int id, String title, String manufacturer_data, String release_date, int price, int manufacturer_id) {
        this.id = id;
        this.title = title;
        this.manufacturer_data = manufacturer_data;
        this.release_date = release_date;
        this.price = price;
        this.manufacturer_id = manufacturer_id;
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

    public void setTitle(String manufacturer_data) {
        this.title = title;
    }
    
    public String getManufacturer_data() {
        return manufacturer_data;
    }

    public void setManufacturer_data(String release_date) {
        this.manufacturer_data = manufacturer_data;
    }
    
    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", manufacturer_data='" + manufacturer_data + '\'' +
                ", release_date='" + release_date + '\'' +
                ", price='" + price + '\'' +
                ", manufacturer_id='" + manufacturer_id + '\'' +
                '}' + "\n";
    }
    
    
    
    
}

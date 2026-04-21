/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication16;

/**
 *
 * @author Omar
 */
import java.io.Serializable;

public class Meals implements Serializable {
    private int mealId;
    private String name;
    private double price;
    private String category;

    public Meals(int mealId, String name, double price, String category) {
        this.mealId = mealId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters and Setters
    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return  
                "mealId: " + mealId +
                ", name: " + name + '\'' +
                ", price: " + price +
                ", category: " + category + '\''+"\n" ;
                
    }
}


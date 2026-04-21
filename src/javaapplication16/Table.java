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
public class Table implements Serializable {
    private int tableId;
    private String category;
    private int reservationsCount;
    private double revenue;

    public Table(int tableId, String category) {
        this.tableId = tableId;
        this.category = category;
        this.reservationsCount = 0;
        this.revenue = 0;
    }

    // Getters and Setters
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getReservationsCount() {
        return reservationsCount;
    }

    public void setReservationsCount(int reservationsCount) {
        this.reservationsCount = reservationsCount;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableId=" + tableId +
                ", category='" + category + '\'' +
                ", reservationsCount=" + reservationsCount +
                ", revenue=" + revenue +
                '}';
    }
    
}


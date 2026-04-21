/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication16;

/**
 *
 * @author omar
 */
import java.io.Serializable;
import java.util.Date;
import java.util.*;

public class Reservation implements Serializable {
    private double reservationId;
    private Guest guest;
    private Table table;
    private ArrayList<Meals> meals;
    private double totalPayment;
    private Date date;

    public Reservation(double reservationId, Guest guest, Table table, ArrayList<Meals> meals, double totalPayment, Date date) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.table = table;
        this.meals = meals;
        this.totalPayment = totalPayment;
        this.date = date;
    }

    // Getters and Setters
    public double getReservationId() {
        return reservationId;
    }

    public void setReservationId(double reservationId) {
        this.reservationId = reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public ArrayList<Meals> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meals> meals) {
        this.meals = meals;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reservation" +"\n"+
                "reservationId: " + reservationId + "\n"+
                "guest: " + guest.getUsername() + "\n"+
                "table: " + table.getTableId() + "\n"+
                "meals: " + meals + "\n"+
                "totalPayment: " + totalPayment + "\n"+
                "date: " + date ;
                
    }
}

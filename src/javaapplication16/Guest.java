package javaapplication16;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class Guest extends User {

    private ArrayList<Reservation> reservationHistory;

    public Guest(String username, String password) {
        super(username, password);
        this.reservationHistory = new ArrayList<>();
    }


    public ArrayList<Reservation> getReservationHistory() {
        return reservationHistory;
    }

    public void setReservationHistory(ArrayList<Reservation> reservationHistory) {
        this.reservationHistory = reservationHistory != null ? reservationHistory : new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservationHistory.add(reservation);
    }

public void viewReservationHistory(Stage stage, Scene guestScene) {
    VBox reservationBox = new VBox(30);
    reservationBox.setPadding(new Insets(20));
    reservationBox.setAlignment(Pos.CENTER);

    Label title = new Label("Reservation History");
    title.setFont(Font.font("Courier", FontWeight.BOLD, 18));

    ListView<String> reservationListView = new ListView<>();
    Guest guest = findGuestByUsername(getUsername());

    if (guest != null) {
        boolean hasReservations = false;


        for (Reservation reservation : reservations) {
            if (reservation.getGuest().getUsername().equals(guest.getUsername())) {
                reservationListView.getItems().add(reservation.toString());
                hasReservations = true;
            }
        }


        if (!hasReservations) {
            reservationListView.getItems().add("No reservations found.");
        }
    } else {
        reservationListView.getItems().add("Guest not found.");
    }

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> stage.setScene(guestScene));

    Button clearButton = new Button("Clear All");
    clearButton.setOnAction(e -> {
        reservations.removeIf(reservation -> reservation.getGuest().getUsername().equals(guest.getUsername()));
        reservationListView.getItems().clear();
        saveData(); 
        reservationListView.getItems().add("No reservations found.");
    });

 
    HBox buttonBox = new HBox(20, backButton, clearButton);
    buttonBox.setAlignment(Pos.CENTER);

    reservationBox.getChildren().addAll(title, reservationListView, buttonBox);

    Scene reservationScene = new Scene(reservationBox, 500, 400);
    stage.setScene(reservationScene);
}





    @Override
    public void showDashboard(Stage stage, Scene mainScene) {
        
        Label guestLabel = new Label("Welcome, " + getUsername());
        guestLabel.setFont(Font.font("Courier", 20));

        Button viewReservationsButton = new Button("View Reservation History");
        Button logoutButton = new Button("Logout");

        VBox guestMenu = new VBox(20, guestLabel, viewReservationsButton, logoutButton);
        guestMenu.setSpacing(20);
        guestMenu.setPrefSize(300, 200);
        guestMenu.setAlignment(Pos.CENTER);

        Scene guestScene = new Scene(guestMenu);

        
        viewReservationsButton.setOnAction(e -> viewReservationHistory(stage, guestScene));
        logoutButton.setOnAction(e -> stage.setScene(mainScene));

       
        stage.setScene(guestScene);
        stage.show();
    }

    @Override
    public String toString() {
        return "Guest{" +
                "username='" + username + '\'' +
                ", reservationsCount=" + (reservationHistory != null ? reservationHistory.size() : 0) +
                '}';
    }
}

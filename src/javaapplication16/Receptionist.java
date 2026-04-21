package javaapplication16;

import java.util.ArrayList;
import java.util.Date;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Receptionist extends User {

    public Receptionist(String username, String password) {
        
        super(username, password);
         
    }

    @Override
public void showDashboard(Stage stage, Scene mainScene) {
    Label recLabel = new Label("Receptionist Dashboard");
    recLabel.setFont(Font.font("Courier", FontWeight.BOLD, 18));

    Button bookTableButton = new Button("Book Table");
    Button cancelReservationButton = new Button("Cancel Reservation");
    Button viewReservationsButton = new Button("View All Reservations");
    Button logoutButton = new Button("Logout");

    bookTableButton.setMaxWidth(Double.max(150, 150));
    cancelReservationButton.setMaxWidth(Double.max(150, 150));
    viewReservationsButton.setMaxWidth(Double.max(150, 150));
    logoutButton.setMaxWidth(Double.max(150, 150));

    VBox receptionistMenu = new VBox(15, recLabel, bookTableButton, cancelReservationButton, viewReservationsButton, logoutButton);
    receptionistMenu.setAlignment(Pos.CENTER);
    receptionistMenu.setPadding(new Insets(20));

    Scene receptionistScene = new Scene(receptionistMenu, 400, 300);

    // Set button actions
    logoutButton.setOnAction(e -> stage.setScene(mainScene));

    bookTableButton.setOnAction(e -> showBookingPage(stage, receptionistScene));
    cancelReservationButton.setOnAction(e -> showCancelReservationPage(stage, receptionistScene));
    viewReservationsButton.setOnAction(e -> showAllReservations(stage, receptionistScene));

    stage.setScene(receptionistScene);
}


private void showAllReservations(Stage stage, Scene previousScene) {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.CENTER);

    Label titleLabel = new Label("All Reservations");
    titleLabel.setFont(Font.font("Courier", FontWeight.BOLD, 20));

    ListView<String> reservationsListView = new ListView<>();
    if (reservations.isEmpty()) {
        reservationsListView.getItems().add("No reservations found.");
    } else {
        for (Reservation reservation : reservations) {
            reservationsListView.getItems().add(reservation.toString()); 
        }
    }

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> stage.setScene(previousScene));

    layout.getChildren().addAll(titleLabel, reservationsListView, backButton);

    Scene allReservationsScene = new Scene(layout, 500, 400);
    stage.setScene(allReservationsScene);
}


    private void showBookingPage(Stage stage, Scene previousScene) {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.CENTER);

    Label titleLabel = new Label("Book a Table");
    titleLabel.setFont(Font.font("Courier", FontWeight.BOLD, 20));
    
    Label tableReservationIdLabel = new Label("Create Reservation ID:");
    TextField tableReservationIdField = new TextField();
    Label tableIdLabel = new Label("Enter Table ID:");
    
    TextField tableIdField = new TextField();

    Label numMealsLabel = new Label("Number of Meals:");
    TextField numMealsField = new TextField();

    Label guestUsernameLabel = new Label("Guest Username:");
    TextField guestUsernameField = new TextField();

    Button submitButton = new Button("Submit");
    Button backButton = new Button("Back");
    Label statusLabel = new Label();
    
    submitButton.setMaxWidth(Double.max(130, 130));
    backButton.setMaxWidth(Double.max(130, 130));
    
    submitButton.setOnAction(e -> {
        try {
           int reservationId = Integer.parseInt(tableReservationIdField.getText()); 
            int tableId = Integer.parseInt(tableIdField.getText());
            Table table = findTableById(tableId);

            if (table == null) {
                statusLabel.setText("Table not found!");
                return;
            }

            int numMeals = Integer.parseInt(numMealsField.getText());
            if (numMeals <= 0) {
                statusLabel.setText("Number of meals must be greater than 0!");
                return;
            }

            // Collect selected meals
            ArrayList<Meals> selectedMeals = new ArrayList<>();
              final double[] totalPayment = {0};
            for (int i = 0; i < numMeals; i++) {
                TextInputDialog mealDialog = new TextInputDialog();
                mealDialog.setTitle("Meal Selection");
                mealDialog.setHeaderText("Enter Meal ID for meal " + (i + 1));
                mealDialog.setContentText("Meal ID:");

                mealDialog.showAndWait().ifPresent(mealIdStr -> {
                    int mealId = Integer.parseInt(mealIdStr);
                    Meals meal = findMealById(mealId);

                    if (meal != null) {
                        selectedMeals.add(meal);
                        totalPayment[0] += meal.getPrice(); 
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Meal ID " + mealId + " not found!");
                        alert.showAndWait();
                    }
                });
            }

            String guestUsername = guestUsernameField.getText();
            Guest guest = findGuestByUsername(guestUsername);

            if (guest != null) {
                reservations.add(new Reservation(reservationId , guest, table, selectedMeals, totalPayment[0], new Date()));
                saveData();
                statusLabel.setText("Reservation created successfully!");
            } else {
                statusLabel.setText("Guest not found!");
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid input! Please enter valid numbers.");
        }
    });

    backButton.setOnAction(e -> stage.setScene(previousScene));
    
    
    
    layout.getChildren().addAll(
            titleLabel,
            tableReservationIdLabel, tableReservationIdField,
            tableIdLabel, tableIdField,
            numMealsLabel, numMealsField,
            guestUsernameLabel, guestUsernameField,
            submitButton, backButton,
            statusLabel
    );
    Scene bookingScene = new Scene(layout, 400, 400);
    stage.setScene(bookingScene);
}




    private void showCancelReservationPage(Stage stage, Scene previousScene) {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.CENTER);

    Label titleLabel = new Label("Cancel Reservation");
    titleLabel.setFont(Font.font("Courier", FontWeight.BOLD, 20));
    
    Label reservationIdLabel = new Label("Enter Reservation ID:");
    TextField reservationIdField = new TextField();

    Button submitButton = new Button("Submit");
    Button backButton = new Button("Back");
    Label statusLabel = new Label();
    
    submitButton.setMaxWidth(Double.max(130, 130));
    backButton.setMaxWidth(Double.max(130, 130));
    
    submitButton.setOnAction(e -> {
        try {
            double reservationId = Double.parseDouble(reservationIdField.getText());
            Reservation reservation = findReservationById(reservationId);

            if (reservation != null) {
                reservation.getGuest().getReservationHistory().remove(reservation);
                saveData();
                statusLabel.setText("Reservation cancelled successfully!");
            } else {
                statusLabel.setText("Reservation not found!");
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid input! Please enter a valid Reservation ID.");
        }
    });

    backButton.setOnAction(e -> stage.setScene(previousScene));

    layout.getChildren().addAll(
            titleLabel,
            reservationIdLabel, reservationIdField,
            submitButton, backButton,
            statusLabel
    );
    Scene cancelReservationScene = new Scene(layout, 400, 300);
    stage.setScene(cancelReservationScene);
}


    @Override
    public String toString() {
        return "Receptionist{" +
                "username='" + username + '\'' +
                '}';
    }
}

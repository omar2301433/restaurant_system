package javaapplication16;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RestaurantManagementSystem extends Application {

    @Override
    public void start(Stage primaryStage) {
        Admin admin = new Admin("admin", "admin123");
       
        Receptionist receptionist = new Receptionist("receptionist", "reception123");
        Guest guest = new Guest("guest", "guest123");
         
        Label mainLabel = new Label("Welcome to the Restaurant Management System");
         mainLabel.setFont(Font.font("Courier", FontWeight.BOLD, 15));
        
        Button adminLoginButton = new Button("Admin Login");
        Button receptionistLoginButton = new Button("Receptionist Login");
        Button guestLoginButton = new Button("Guest Login");
        Button exitButton = new Button("Exit");
        
        adminLoginButton.setMaxWidth(Double.max(170, 170));
        receptionistLoginButton.setMaxWidth(Double.max(170, 170));
        guestLoginButton.setMaxWidth(Double.max(170, 170));
        exitButton.setMaxWidth(Double.max(170, 170));
        
        VBox mainMenu = new VBox(15, mainLabel, adminLoginButton, receptionistLoginButton, guestLoginButton, exitButton);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setPadding(new Insets(20));

        Scene mainScene = new Scene(mainMenu, 400, 300);
 
        adminLoginButton.setOnAction(e -> admin.showLoginScreen(primaryStage, mainScene));
        receptionistLoginButton.setOnAction(e -> receptionist.showLoginScreen(primaryStage, mainScene));
        guestLoginButton.setOnAction(e -> guest.showLoginScreen(primaryStage, mainScene));
        exitButton.setOnAction(e -> primaryStage.close());

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Restaurant Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}

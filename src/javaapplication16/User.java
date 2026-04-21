package javaapplication16;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class User implements Serializable{
     public static ArrayList<Table> tables = new ArrayList<>();
     public static  ArrayList<Meals> meals = new ArrayList<>();
     public static  ArrayList<User> users = new ArrayList<>();
     public static  ArrayList<Reservation> reservations = new ArrayList<>();
    String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setUsername(String username )
    {
        this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public Meals findMealById(int mealId)
   {
       
        for (Meals meal : meals) {
            if (meal.getMealId() == mealId) {
                return meal;
            }
          
   }
          return null;
   }
   public User findMealByName(String username)
   {
       
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
          
   }
          return null;
   }

    public Reservation findReservationById(double reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        
        return null;
    }
    public Guest findGuestByUsername(String username )
    {
         for (User user : users) {
            if (user instanceof Guest guest) {
                if (guest.getUsername().equals(username)) {
                    return guest;
                }
              
            }
        }
           return null;
    }
     public User findUserByUsername(String username )
    {
         for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
        }
           return null;
    }
    public boolean finduserByUsername(String username) {
    
    for (User guest : users) {
        if (guest.getUsername().equals(username)) {
            return true; 
        }
    }

   
    for (User receptionist : users) {
        if (receptionist.getUsername().equals(username)) {
            return true; 
        }
    }

    return false; 
}
    public boolean isTableFound(int tableId)
    {
        boolean isFound= false;
        for(Table table : tables )
        {
            if (table.getTableId()== tableId)
            {
                isFound=true;
            }
        }
        return isFound;
        
    }
     public Table findTableById(int tableId)
   {
       
        for (Table table : tables) {
            if (table.getTableId()== tableId) {
                return table;
            }
          
   }
          return null;
   }
      public void saveData() {
        try (ObjectOutputStream tableOut = new ObjectOutputStream(new FileOutputStream("tables3.dat"));
             ObjectOutputStream mealOut = new ObjectOutputStream(new FileOutputStream("meals3.dat"));
             ObjectOutputStream userOut = new ObjectOutputStream(new FileOutputStream("users3.dat"));
             ObjectOutputStream reservationOut = new ObjectOutputStream(new FileOutputStream("reservations3.dat"))) {

            tableOut.writeObject(tables);
            mealOut.writeObject(meals);
            userOut.writeObject(users);
            reservationOut.writeObject(reservations);
            
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println(" ERROR SAVIMG DATA: "+e.getMessage());
        }
    }

 public void loadData() {
        try (ObjectInputStream tablein = new ObjectInputStream(new FileInputStream("tables3.dat"));
             ObjectInputStream mealin= new ObjectInputStream(new FileInputStream("meals3.dat"));
             ObjectInputStream userin = new ObjectInputStream(new FileInputStream("users3.dat"));
             ObjectInputStream reservationin = new ObjectInputStream(new FileInputStream("reservations3.dat"))) {
            
            tables = (ArrayList<Table>) tablein.readObject();
             meals = (ArrayList<Meals>) mealin.readObject();
              users = (ArrayList<User>) userin.readObject();
               reservations = (ArrayList<Reservation>) reservationin.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error LOADING DATA: " + e.getMessage());
        }
    }

    public void showLoginScreen(Stage stage, Scene mainScene) {
         
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");
        
        loginButton.setMaxWidth(Double.max(70, 70));
        backButton.setMaxWidth(Double.max(70, 70));
        
        VBox loginLayout = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, backButton);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(20));

        Scene loginScene = new Scene(loginLayout, 400, 300);

        loginButton.setOnAction(e -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();
            
            User loggedInUser = findUserByUsername(enteredUsername);
            users.add(new Admin("admin","admin123"));
            saveData();
            if(validateLogin(enteredUsername, enteredPassword))
                    {
                        
                       showDashboard(stage,mainScene) ;
                    }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials!", ButtonType.OK);
                 alert.show();
                
            }
             if (validateLogin(enteredUsername, enteredPassword)) {
       
        if (loggedInUser instanceof Guest) {
            // Handle guest dashboard
            Guest guest = (Guest) loggedInUser;
            guest.showDashboard(stage, mainScene); 
        } else if (loggedInUser instanceof Receptionist) {
            // Handle admin dashboard
            Receptionist receptionist = (Receptionist) loggedInUser;
            receptionist.showDashboard(stage, mainScene); 
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials!", ButtonType.OK);
        alert.show();
    }
});

        backButton.setOnAction(e -> stage.setScene(mainScene));

        stage.setScene(loginScene);
    }

    public abstract void showDashboard(Stage stage, Scene mainScene);

public boolean validateLogin(String enteredUsername, String enteredPassword) {
  
    if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
        return true;
    }

    for (User user : users) {
        if (user.getUsername().equals(enteredUsername) && user.getPassword().equals(enteredPassword)) {
            return true;
        }
    }

    return false; 
}

     

     
}

    

                 
    


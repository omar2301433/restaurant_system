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

public class Admin extends User {
   

    public Admin(String username, String password) {
        super(username, password);
        
        loadData();
    }

    @Override
     public void showDashboard(Stage stage, Scene mainScene) {
        Label adminLabel = new Label("Admin Dashboard");
        adminLabel.setFont(Font.font("Courier", FontWeight.BOLD, 20));

        Button manageTablesButton = new Button("Manage Tables");
        Button manageMealsButton = new Button("Manage Meals");
        Button manageUsersButton = new Button("Manage Users");
        Button logoutButton = new Button("Logout");
        
       manageTablesButton.setMaxWidth(Double.max(150, 150));
        manageMealsButton.setMaxWidth(Double.max(150, 150));
        manageUsersButton.setMaxWidth(Double.max(150, 150));
        logoutButton.setMaxWidth(Double.max(150, 150));

        VBox adminMenu = new VBox(15, adminLabel, manageTablesButton, manageMealsButton, manageUsersButton, logoutButton);
        adminMenu.setAlignment(Pos.CENTER);
        adminMenu.setPadding(new Insets(20));

        Scene adminScene = new Scene(adminMenu, 400, 300);

        manageTablesButton.setOnAction(e -> showManageTablesScreen(stage, adminScene));
        manageMealsButton.setOnAction(e -> showManageMealsScreen(stage, adminScene));
        manageUsersButton.setOnAction(e -> showManageUsersScreen(stage, adminScene));
        logoutButton.setOnAction(e -> stage.setScene(mainScene));

        stage.setScene(adminScene);
    }

  
    private void showManageTablesScreen(Stage stage, Scene mainScene) {
        Label label = new Label("Manage Tables");
        label.setFont(Font.font("Courier", FontWeight.BOLD, 20));

        Button addTableButton = new Button("Add Table");
        Button editTableButton = new Button("Edit Table");
        Button deleteTableButton = new Button("Delete Table");
        Button viewTablesButton = new Button("View Tables");
        Button backButton = new Button("Back");
        
        addTableButton.setMaxWidth(Double.max(130, 130));
        editTableButton.setMaxWidth(Double.max(130, 130));
        deleteTableButton.setMaxWidth(Double.max(130, 130));
        viewTablesButton.setMaxWidth(Double.max(130, 130));
        backButton.setMaxWidth(Double.max(130, 130));
        
        VBox layout = new VBox(15, label, addTableButton, editTableButton, deleteTableButton, viewTablesButton, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene tableScene = new Scene(layout, 400, 300);

        backButton.setOnAction(e -> stage.setScene(mainScene));

        addTableButton.setOnAction(e -> handleAddTable());
        editTableButton.setOnAction(e -> handleEditTable());
        deleteTableButton.setOnAction(e -> handleDeleteTable());
        viewTablesButton.setOnAction(e -> handleViewTables(stage, tableScene));

        stage.setScene(tableScene);
    }

  private void handleAddTable() {
    TextField tableIdField = new TextField();
    TextField categoryField = new TextField();

    Button addButton = new Button("Add Table");
    addButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label tableIdLabel = new Label("Table ID:");
    tableIdLabel.setMinWidth(labelWidth);

    Label categoryLabel = new Label("Category:");
    categoryLabel.setMinWidth(labelWidth);

    HBox tableIdBox = new HBox(10, tableIdLabel, tableIdField);
    tableIdBox.setAlignment(Pos.CENTER_LEFT);

    HBox categoryBox = new HBox(10, categoryLabel, categoryField);
    categoryBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Enter Table ID and Category:"), tableIdBox, categoryBox, addButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Scene dialogScene = new Scene(vbox, 368, 250);
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Add Table");
    dialogStage.setScene(dialogScene);

    addButton.setOnAction(e -> {
        String tableIdText = tableIdField.getText();
        String category = categoryField.getText();
        try {
            if (isTableFound(Integer.parseInt(tableIdText))) {
                showAlert(Alert.AlertType.ERROR, "Error", "This table already exists.");
            } else if (!tableIdText.isEmpty() && !category.isEmpty()) {
                tables.add(new Table(Integer.parseInt(tableIdText), category)); // Assuming 'tables' is a list of Table objects
                showAlert(Alert.AlertType.INFORMATION, "Table Added", "Table successfully added!");
                saveData();
                dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Both fields are required!");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Table ID! Please enter a valid number.");
        }
    });

    dialogStage.show();
}




    private void handleEditTable() {

    TextField tableIdField = new TextField();
    TextField categoryField = new TextField();

    Button saveButton = new Button("Save Changes");
    saveButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label tableIdLabel = new Label("Table ID:");
    tableIdLabel.setMinWidth(labelWidth);

    Label newCategoryLabel = new Label("New Category:");
    newCategoryLabel.setMinWidth(labelWidth);

    HBox tableIdBox = new HBox(10, tableIdLabel, tableIdField);
    tableIdBox.setAlignment(Pos.CENTER_LEFT);

    HBox categoryBox = new HBox(10, newCategoryLabel, categoryField);
    categoryBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Edit Table"), tableIdBox, categoryBox, saveButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Edit Table");
    dialogStage.setScene(new Scene(vbox, 368, 250));

    saveButton.setOnAction(e -> {
        String tableIdText = tableIdField.getText();
        String newCategory = categoryField.getText();

        if (!tableIdText.isEmpty() && !newCategory.isEmpty()) {
            try {
                int tableId = Integer.parseInt(tableIdText);
                Table table = findTableById(tableId); 
                if (table != null) {
                    table.setCategory(newCategory); 
                    showAlert(Alert.AlertType.INFORMATION, "Table Edited", "Table successfully updated!");
                    saveData();
                    dialogStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Table not found!");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Table ID! Please enter a valid number.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Both fields are required!");
        }
    });

    dialogStage.show();
}


  private void handleDeleteTable() {

     TextField tableIdField = new TextField();

    Button deleteButton = new Button("Delete Table");
    deleteButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label tableIdLabel = new Label("Table ID:");
    tableIdLabel.setMinWidth(labelWidth);

    HBox tableIdBox = new HBox(10, tableIdLabel, tableIdField);
    tableIdBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Delete Table"), tableIdBox, deleteButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Delete Table");
    dialogStage.setScene(new Scene(vbox, 368, 250));

    deleteButton.setOnAction(e -> {
        String tableIdText = tableIdField.getText();

        if (!tableIdText.isEmpty()) {
            try {
                int tableId = Integer.parseInt(tableIdText);
                Table table = findTableById(tableId);
                if (table != null) {
                    tables.remove(table);
                    showAlert(Alert.AlertType.INFORMATION, "Table Deleted", "Table successfully removed!");
                    saveData();
                    dialogStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Table not found!");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Table ID! Please enter a valid number.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Table ID is required!");
        }
    });

    dialogStage.show();
}


    private void handleViewTables(Stage stage, Scene previousScene) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("View All Tables");
        label.setFont(Font.font("Courier", FontWeight.BOLD, 20));
        layout.getChildren().add(label);

        for (Table table : tables) {
            layout.getChildren().add(new Label(table.toString()));
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(previousScene));
        layout.getChildren().add(backButton);

        stage.setScene(new Scene(layout, 400, 300));
    }
    
 private void showManageMealsScreen(Stage stage, Scene mainScene) {
        Label label = new Label("Manage Meals");
        label.setFont(Font.font("Courier", FontWeight.BOLD, 20));

        Button addMealButton = new Button("Add Meal");
        Button editMealButton = new Button("Edit Meal");
        Button deleteMealButton = new Button("Delete Meal");
        Button viewMealsButton = new Button("View Meals");
        Button backButton = new Button("Back");
        
        addMealButton.setMaxWidth(Double.max(130, 130));
        editMealButton.setMaxWidth(Double.max(130, 130));
        deleteMealButton.setMaxWidth(Double.max(130, 130));
        viewMealsButton.setMaxWidth(Double.max(130, 130));
        backButton.setMaxWidth(Double.max(130, 130));

        VBox layout = new VBox(15, label, addMealButton, editMealButton, deleteMealButton, viewMealsButton, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene mealScene = new Scene(layout, 400, 300);

        backButton.setOnAction(e -> stage.setScene(mainScene));

        addMealButton.setOnAction(e -> handleAddMeal());
        editMealButton.setOnAction(e -> handleEditMeal());
        deleteMealButton.setOnAction(e -> handleDeleteMeal());
        viewMealsButton.setOnAction(e -> handleViewMeals(stage, mealScene));

        stage.setScene(mealScene);
    }

 


    
    private void handleAddMeal() {
    TextField mealIdField = new TextField();
    TextField mealNameField = new TextField();
    TextField mealPriceField = new TextField();
    TextField mealCategoryField = new TextField();

    Button addButton = new Button("Add Meal");
    addButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label mealIdLabel = new Label("Meal ID:");
    mealIdLabel.setMinWidth(labelWidth);

    Label mealNameLabel = new Label("Meal Name:");
    mealNameLabel.setMinWidth(labelWidth);

    Label mealPriceLabel = new Label("Meal Price:");
    mealPriceLabel.setMinWidth(labelWidth);

    Label mealCategoryLabel = new Label("Meal Category:");
    mealCategoryLabel.setMinWidth(labelWidth);

    HBox mealIdBox = new HBox(10, mealIdLabel, mealIdField);
    mealIdBox.setAlignment(Pos.CENTER_LEFT);

    HBox mealNameBox = new HBox(10, mealNameLabel, mealNameField);
    mealNameBox.setAlignment(Pos.CENTER_LEFT);

    HBox mealPriceBox = new HBox(10, mealPriceLabel, mealPriceField);
    mealPriceBox.setAlignment(Pos.CENTER_LEFT);

    HBox mealCategoryBox = new HBox(10, mealCategoryLabel, mealCategoryField);
    mealCategoryBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Enter Meal Details:"), mealIdBox, mealNameBox, mealPriceBox, mealCategoryBox, addButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Scene dialogScene = new Scene(vbox, 368, 270);
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Add Meal");
    dialogStage.setScene(dialogScene);

    addButton.setOnAction(e -> {
        String mealIdText = mealIdField.getText();
        String mealName = mealNameField.getText();
        String mealPriceText = mealPriceField.getText();
        String mealCategory = mealCategoryField.getText();

        if (!mealIdText.isEmpty() && !mealName.isEmpty() && !mealPriceText.isEmpty() && !mealCategory.isEmpty()) {
            try {
                int mealId = Integer.parseInt(mealIdText);
                double mealPrice = Double.parseDouble(mealPriceText);

                meals.add(new Meals(mealId, mealName, mealPrice, mealCategory));
                showAlert(Alert.AlertType.INFORMATION, "Meal Added", "Meal successfully added!");
                saveData();
                dialogStage.close();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input! Please enter valid numbers.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
        }
    });

    dialogStage.show();
}

private void handleEditMeal() {
    TextField mealIdField = new TextField();
    TextField mealNameField = new TextField();
    TextField mealPriceField = new TextField();
    TextField mealCategoryField = new TextField();

    Button saveButton = new Button("Save Changes");
    saveButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label mealIdLabel = new Label("Meal ID:");
    mealIdLabel.setMinWidth(labelWidth);

    Label newNameLabel = new Label("New Name:");
    newNameLabel.setMinWidth(labelWidth);

    Label newPriceLabel = new Label("New Price:");
    newPriceLabel.setMinWidth(labelWidth);

    Label newCategoryLabel = new Label("New Category:");
    newCategoryLabel.setMinWidth(labelWidth);

    HBox mealIdBox = new HBox(10, mealIdLabel, mealIdField);
    mealIdBox.setAlignment(Pos.CENTER_LEFT);

    HBox mealNameBox = new HBox(10, newNameLabel, mealNameField);
    mealNameBox.setAlignment(Pos.CENTER_LEFT);

    HBox mealPriceBox = new HBox(10, newPriceLabel, mealPriceField);
    mealPriceBox.setAlignment(Pos.CENTER_LEFT);

    HBox mealCategoryBox = new HBox(10, newCategoryLabel, mealCategoryField);
    mealCategoryBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Edit Meal"), mealIdBox, mealNameBox, mealPriceBox, mealCategoryBox, saveButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Edit Meal");
    dialogStage.setScene(new Scene(vbox, 368, 270));

    saveButton.setOnAction(e -> {
        String mealIdText = mealIdField.getText();
        String newName = mealNameField.getText();
        String newPriceText = mealPriceField.getText();
        String newCategory = mealCategoryField.getText();

        if (!mealIdText.isEmpty() && !newName.isEmpty() && !newPriceText.isEmpty()) {
            try {
                int mealId = Integer.parseInt(mealIdText);
                double newPrice = Double.parseDouble(newPriceText);

                Meals meal = findMealById(mealId);
                if (meal != null) {
                    meal.setName(newName);
                    meal.setPrice(newPrice);
                    meal.setCategory(newCategory);
                    showAlert(Alert.AlertType.INFORMATION, "Meal Updated", "Meal details successfully updated!");
                    saveData();
                    dialogStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Meal not found!");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input! Please enter valid numbers.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
        }
    });

    dialogStage.show();
}

private void handleDeleteMeal() {
    TextField mealIdField = new TextField();

    Button deleteButton = new Button("Delete Meal");
    deleteButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label mealIdLabel = new Label("Meal ID:");
    mealIdLabel.setMinWidth(labelWidth);

    HBox mealIdBox = new HBox(10, mealIdLabel, mealIdField);
    mealIdBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Delete Meal"), mealIdBox, deleteButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Delete Meal");
    dialogStage.setScene(new Scene(vbox, 368, 270));

    deleteButton.setOnAction(e -> {
        String mealIdText = mealIdField.getText();

        if (!mealIdText.isEmpty()) {
            try {
                int mealId = Integer.parseInt(mealIdText);
                Meals meal = findMealById(mealId);
                if (meal != null) {
                    meals.remove(meal);
                    showAlert(Alert.AlertType.INFORMATION, "Meal Deleted", "Meal successfully removed!");
                    saveData();
                    dialogStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Meal not found!");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Meal ID! Please enter a valid number.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Meal ID is required!");
        }
    });

    dialogStage.show();
}



    private void handleViewMeals(Stage stage, Scene previousScene) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("View All Meals");
        label.setFont(Font.font("Courier", FontWeight.BOLD, 20));
        layout.getChildren().add(label);

        for (Meals meal : meals) {
            layout.getChildren().add(new Label(meal.toString()));
        }

        Button backButton = new Button("Back");
        backButton.setMaxWidth(Double.max(130, 130));
        
        backButton.setOnAction(e -> stage.setScene(previousScene));
        layout.getChildren().add(backButton);

        stage.setScene(new Scene(layout, 400, 300));
    }

private void showManageUsersScreen(Stage stage, Scene mainScene) {
        Label label = new Label("Manage Users");
        label.setFont(Font.font("Courier", FontWeight.BOLD, 20));

        Button addUserButton = new Button("Add User");
        Button editUserButton = new Button("Edit User");
        Button deleteUserButton = new Button("Delete User");
        Button viewUsersButton = new Button("View Users");
        Button backButton = new Button("Back");
        
        addUserButton.setMaxWidth(Double.max(130, 130));
        editUserButton.setMaxWidth(Double.max(130, 130));
        deleteUserButton.setMaxWidth(Double.max(130, 130));
        viewUsersButton.setMaxWidth(Double.max(130, 130));
        backButton.setMaxWidth(Double.max(130, 130));

        VBox layout = new VBox(15, label, addUserButton, editUserButton, deleteUserButton, viewUsersButton, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene userScene = new Scene(layout, 400, 300);

        backButton.setOnAction(e -> stage.setScene(mainScene));

        addUserButton.setOnAction(e -> handleAddUser());
        editUserButton.setOnAction(e -> handleEditUser());
        deleteUserButton.setOnAction(e -> handleDeleteUser());
        viewUsersButton.setOnAction(e -> handleViewUsers(stage, userScene));

        stage.setScene(userScene);
    }


   private void handleAddUser() {
    TextField usernameField = new TextField();
    TextField passwordField = new TextField();
    ComboBox<String> roleBox = new ComboBox<>();
    roleBox.getItems().addAll("Guest", "Receptionist");

    Button addButton = new Button("Add User");
    addButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label usernameLabel = new Label("Username:");
    usernameLabel.setMinWidth(labelWidth);

    Label passwordLabel = new Label("Password:");
    passwordLabel.setMinWidth(labelWidth);

    Label roleLabel = new Label("Role:");
    roleLabel.setMinWidth(labelWidth);

    HBox usernameBox = new HBox(10, usernameLabel, usernameField);
    usernameBox.setAlignment(Pos.CENTER_LEFT);

    HBox passwordBox = new HBox(10, passwordLabel, passwordField);
    passwordBox.setAlignment(Pos.CENTER_LEFT);

    HBox roleBoxLayout = new HBox(10, roleLabel, roleBox);
    roleBoxLayout.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Enter User Details:"), usernameBox, passwordBox, roleBoxLayout, addButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Add User");
    dialogStage.setScene(new Scene(vbox, 368, 250));

    addButton.setOnAction(e -> {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleBox.getValue();

        if (!username.isEmpty() && !password.isEmpty() && role != null) {
            if (!finduserByUsername(username)) {
                if (role.equals("Guest")) {
                    users.add(new Guest(username, password));
                } else if (role.equals("Receptionist")) {
                    users.add(new Receptionist(username, password));
                }
                showAlert(Alert.AlertType.INFORMATION, "User Added", "User successfully added!");
                saveData();
                dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "This username already exists.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
        }
    });

    dialogStage.show();
}

private void handleEditUser() {
    TextField usernameField = new TextField();
    PasswordField passwordField = new PasswordField();

    Button saveButton = new Button("Submit");
    saveButton.setMaxWidth(Double.max(130, 130));
    // Set common label width
    double labelWidth = 80;

    Label usernameLabel = new Label("Username:");
    usernameLabel.setMinWidth(labelWidth);

    Label passwordLabel = new Label("Password:");
    passwordLabel.setMinWidth(labelWidth);

    HBox usernameBox = new HBox(10, usernameLabel, usernameField);
    usernameBox.setAlignment(Pos.CENTER_LEFT);

    HBox passwordBox = new HBox(10, passwordLabel, passwordField);
    passwordBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Enter Username and Password:"), usernameBox, passwordBox, saveButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Edit User");
    dialogStage.setScene(new Scene(vbox, 368, 250));

    saveButton.setOnAction(e -> {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.validateLogin(username, password)) {
                        showEditOptions(user, dialogStage);
                        return;
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Invalid password!");
                        return;
                    }
                }
            }
            showAlert(Alert.AlertType.ERROR, "Error", "User not found!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Both fields are required!");
        }
    });

    dialogStage.show();
}

private void showEditOptions(User user, Stage parentStage) {
    TextField newUsernameField = new TextField(user.getUsername());
    PasswordField newPasswordField = new PasswordField();
    PasswordField confirmPasswordField = new PasswordField();

    Button saveButton = new Button("Save Changes");
    saveButton.setMaxWidth(Double.max(130, 130));
    // Set common label width
    double labelWidth = 120;

    Label newUsernameLabel = new Label("New Username:");
    newUsernameLabel.setMinWidth(labelWidth);

    Label newPasswordLabel = new Label("New Password:");
    newPasswordLabel.setMinWidth(labelWidth);

    Label confirmPasswordLabel = new Label("Confirm Password:");
    confirmPasswordLabel.setMinWidth(labelWidth);

    HBox newUsernameBox = new HBox(10, newUsernameLabel, newUsernameField);
    newUsernameBox.setAlignment(Pos.CENTER_LEFT);

    HBox newPasswordBox = new HBox(10, newPasswordLabel, newPasswordField);
    newPasswordBox.setAlignment(Pos.CENTER_LEFT);

    HBox confirmPasswordBox = new HBox(10, confirmPasswordLabel, confirmPasswordField);
    confirmPasswordBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Edit User Details:"), newUsernameBox, newPasswordBox, confirmPasswordBox, saveButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Edit User");
    dialogStage.setScene(new Scene(vbox, 450, 300));

    saveButton.setOnAction(e -> {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!newUsername.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            if (newPassword.equals(confirmPassword)) {
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                showAlert(Alert.AlertType.INFORMATION, "User Updated", "User details successfully updated!");
                saveData();
                dialogStage.close();
                parentStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
        }
    });

    dialogStage.show();
}

private void handleDeleteUser() {
    TextField usernameField = new TextField();

    Button deleteButton = new Button("Delete User");
    deleteButton.setMaxWidth(Double.max(130, 130));

    // Set common label width
    double labelWidth = 80;

    Label usernameLabel = new Label("Username:");
    usernameLabel.setMinWidth(labelWidth);

    HBox usernameBox = new HBox(10, usernameLabel, usernameField);
    usernameBox.setAlignment(Pos.CENTER_LEFT);

    VBox vbox = new VBox(15, new Label("Delete User:"), usernameBox, deleteButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(20));

    Stage dialogStage = new Stage();
    dialogStage.setTitle("Delete User");
    dialogStage.setScene(new Scene(vbox, 368, 250));

    deleteButton.setOnAction(e -> {
        String username = usernameField.getText();

        if (!username.isEmpty()) {
            if (finduserByUsername(username)) {
                users.removeIf(user -> user.getUsername().equals(username));
                showAlert(Alert.AlertType.INFORMATION, "User Deleted", "User successfully deleted!");
                saveData();
                dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User not found.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Username is required!");
        }
    });

    dialogStage.show();
}

   
private void handleViewUsers(Stage stage, Scene previousScene) {
    VBox layout = new VBox(15);
    layout.setAlignment(Pos.CENTER);

    Label label = new Label("View All Users");
    label.setFont(Font.font("Courier", FontWeight.BOLD, 20));
    layout.getChildren().add(label);

    for (User user : users) {
        if (finduserByUsername(user.getUsername())) {
            String role = "Unknown";

            if (user instanceof Guest) {
                role = "Guest";
            } else if (user instanceof Receptionist) {
                role = "Receptionist";
            }

            HBox userBox = new HBox(15);
            userBox.setAlignment(Pos.CENTER);
            userBox.getChildren().addAll(
                    new Label("Username: " + user.getUsername()), 
                    new Label("Role: " + role)
            );

            layout.getChildren().add(userBox);
        }
    }

    Button backButton = new Button("Back");
    
    backButton.setMaxWidth(Double.max(130, 130));
    backButton.setOnAction(e -> stage.setScene(previousScene));
    layout.getChildren().add(backButton);

    stage.setScene(new Scene(layout,400,300));
}
 
      
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}




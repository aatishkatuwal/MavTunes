package com.example.hw2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String.*;
import java.util.ArrayList;


public class HelloController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField zipField;

    @FXML
    private CheckBox appCheckBox;
    @FXML
    private CheckBox musicCheckBox;
    @FXML
    private ToggleGroup appType;

    @FXML
    private ComboBox<String> musicCBox;
    @FXML
    private RadioButton gameButton;
    @FXML
    private RadioButton productButton;
    @FXML
    private RadioButton educationButton;

    @FXML
    private TextField titleField;
    @FXML
    private TextField dpField;
    @FXML
    private TextField anField;
    @FXML
    private Button submitButton;
    @FXML
    private  Button finishButton;
    @FXML
    private String popSelection;
    @FXML
    private String classicalSelection;
    @FXML
    private String countrySelection;
    @FXML
    public void finishButtonClick(){
        Platform.exit();
    }
    @FXML
    public void submitButtonClick() {
        if (validateFields()) {
            if (appCheckBox.isSelected()) {
                saveData("app.txt");
            } else if (musicCheckBox.isSelected()) {
                saveData("music.txt");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select either APP or MUSIC checkbox.");
                return;
            }
            clearFields();
            nameField.requestFocus();
            System.out.println("All fields are filled. Proceeding with the submission...");
        }
    }
    private void saveData(String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.println(CollectCustomerData());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save customer data.");
        }
    }
    private String CollectCustomerData() {
        StringBuilder data = new StringBuilder();
        data.append("Name: ").append(nameField.getText()).append("\n");
        data.append("Street: ").append(streetField.getText()).append("\n");
        data.append("City: ").append(cityField.getText()).append("\n");
        data.append("State: ").append(stateField.getText()).append("\n");
        data.append("Zip: ").append(zipField.getText()).append("\n");
        data.append("Title: ").append(titleField.getText()).append("\n");
        data.append("Date Purchased: ").append(dpField.getText()).append("\n");
        data.append("Account Number: ").append(anField.getText()).append("\n");

        return data.toString();
    }
    private void clearFields() {
        nameField.clear();
        streetField.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        titleField.clear();
        dpField.clear();
        anField.clear();

        appCheckBox.setSelected(false);
        musicCheckBox.setSelected(false);
        gameButton.setSelected(false);
        productButton.setSelected(false);
        educationButton.setSelected(false);

        musicCBox.getSelectionModel().clearSelection();
    }
    private boolean validateFields() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Please fill in the following fields:\n");
        TextField firstEmptyField = null;

        if (nameField.getText().isEmpty() && firstEmptyField == null) {
            errorMessage.append("- Name\n");
            firstEmptyField = nameField;
            isValid = false;
        }
        if (streetField.getText().isEmpty() && firstEmptyField == null) {
            errorMessage.append("- Street\n");
            firstEmptyField = streetField;
            isValid = false;
        }
        if (cityField.getText().isEmpty() && firstEmptyField == null) {
            errorMessage.append("- City\n");
            firstEmptyField = cityField;
            isValid = false;
        }
        if (stateField.getText().isEmpty() && firstEmptyField == null) {
            errorMessage.append("- State\n");
            firstEmptyField = stateField;
            isValid = false;
        }
        if (zipField.getText().isEmpty() && firstEmptyField == null) {
            errorMessage.append("- Zip\n");
            firstEmptyField = zipField;
            isValid = false;
        }
        if (!isValid) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", errorMessage.toString());
            firstEmptyField.requestFocus();
        }

        return isValid;
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void changeABCheckBox(){
        if (appCheckBox.isSelected()){
            musicCheckBox.setDisable(true);
            musicCBox.setDisable(true);
        }
        else{
            musicCheckBox.setDisable(false);
            musicCBox.setDisable(false);
        }
    }
    public void changeMBCheckBox(){
        if (musicCheckBox.isSelected()){
            gameButton.setDisable(true);
            productButton.setDisable(true);
            educationButton.setDisable(true);
        }
        else{gameButton.setDisable(false);
            productButton.setDisable(false);
            educationButton.setDisable(false);
        }
    }
    @FXML
    public void onRadioButtonClick(ActionEvent event){
        if (event.getSource().equals(gameButton)){
            System.out.println("Type of App: Game");
        } else if (event.getSource().equals(productButton)) {
            System.out.println("Type of App: Productivity");
        } else if (event.getSource().equals(educationButton)) {
            System.out.println("Type of App: Education");
        }
    }
    @FXML
    public void changeComboBox(ActionEvent ev){
        if (ev.getSource().equals(popSelection)){
            System.out.println("Type of Music: Pop");
        } else if (ev.getSource().equals(classicalSelection)) {
            System.out.println("Type of Music: Classical");
        } else if (ev.getSource().equals(countrySelection)) {
            System.out.println("Type of Music: Country");
        }
    }
}
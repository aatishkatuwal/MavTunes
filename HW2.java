package com.example.hw2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HW2 extends Application {
    private TextField nameField = new TextField();
    private TextField streetField = new TextField();
    private TextField cityField = new TextField();
    private TextField stateField = new TextField();
    private TextField zipField = new TextField();

    private CheckBox appCheckBox = new CheckBox("APP");
    private CheckBox musicCheckBox = new CheckBox("MUSIC");

    private ComboBox<String> musicType = new ComboBox<>();

    private RadioButton gameRadio = new RadioButton("GAME");
    private RadioButton productivityRadio = new RadioButton("PRODUCTIVITY");
    private RadioButton educationRadio = new RadioButton("EDUCATION");
    private ToggleGroup appTypeGroup = new ToggleGroup();

    private TextField titleField = new TextField();
    private TextField dateField = new TextField();
    private TextField accountField = new TextField();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mav Tunes");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(7);
        grid.setVgap(7);

        musicType.getItems().addAll("CHOOSE ONE", "Rock", "Pop", "Jazz", "Classical");
        musicType.getSelectionModel().selectFirst();

        gameRadio.setToggleGroup(appTypeGroup);
        productivityRadio.setToggleGroup(appTypeGroup);
        educationRadio.setToggleGroup(appTypeGroup);

        setPrefWidths();

        appCheckBox.setOnAction(e -> {
            if (appCheckBox.isSelected()) musicCheckBox.setSelected(false);
            updateUI();
        });

        musicCheckBox.setOnAction(e -> {
            if (musicCheckBox.isSelected()) appCheckBox.setSelected(false);
            updateUI();
        });

        Button submitButton = new Button("SUBMIT");
        Button finishButton = new Button("FINISH");

        submitButton.setStyle("-fx-background-radius: 15; -fx-border-radius: 20;");
        finishButton.setStyle("-fx-background-radius: 15; -fx-border-radius: 20;");
        submitButton.setPrefSize(300, 30);
        finishButton.setPrefSize(300, 30);

        submitButton.setOnAction(e -> handleSubmit());
        finishButton.setOnAction(e -> primaryStage.close());

        layoutGrid(grid, submitButton, finishButton);

        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setPrefWidths() {
        TextField[] fields = {nameField, streetField, cityField, stateField, zipField, titleField, dateField, accountField};
        for (TextField field : fields) field.setPrefWidth(250);
        musicType.setPrefWidth(250);
    }

    private void layoutGrid(GridPane grid, Button submitButton, Button finishButton) {
        grid.add(new Label("Name"), 0, 0); grid.add(nameField, 1, 0);
        grid.add(new Label("Street"), 0, 1); grid.add(streetField, 1, 1);
        grid.add(new Label("City"), 0, 2); grid.add(cityField, 1, 2);
        grid.add(new Label("State"), 0, 3); grid.add(stateField, 1, 3);
        grid.add(new Label("Zip"), 0, 4); grid.add(zipField, 1, 4);

        grid.add(new Label("Choose One"), 0, 5);
        grid.add(new HBox(20, appCheckBox, musicCheckBox), 1, 5);

        grid.add(new Label("Type of Music"), 0, 6); grid.add(musicType, 1, 6);

        grid.add(new Label("Type of App"), 0, 7);
        grid.add(new HBox(20, gameRadio, productivityRadio, educationRadio), 1, 7);

        grid.add(new Label("Enter Title"), 0, 8); grid.add(titleField, 1, 8);
        grid.add(new Label("Date Purchased"), 0, 9); grid.add(dateField, 1, 9);
        grid.add(new Label("Account Number"), 0, 10); grid.add(accountField, 1, 10);

        grid.add(submitButton, 0, 11);
        grid.add(finishButton, 1, 11);
    }

    private void updateUI() {
        boolean isApp = appCheckBox.isSelected();
        boolean isMusic = musicCheckBox.isSelected();

        gameRadio.setDisable(isMusic);
        productivityRadio.setDisable(isMusic);
        educationRadio.setDisable(isMusic);

        musicType.setDisable(isApp);

        titleField.setDisable(false);
        dateField.setDisable(false);
        accountField.setDisable(false);
    }

    private void handleSubmit() {
        if (isEmpty(nameField, "Name")) return;
        if (isEmpty(streetField, "Street")) return;
        if (isEmpty(cityField, "City")) return;
        if (isEmpty(stateField, "State")) return;
        if (isEmpty(zipField, "Zip")) return;

        if (!appCheckBox.isSelected() && !musicCheckBox.isSelected()) {
            showAlert("Please select either APP or MUSIC.");
            return;
        }

        if (musicCheckBox.isSelected()) {
            if (musicType.getValue().equals("CHOOSE ONE")) {
                showAlert("Please select a music type.");
                return;
            }
            writeToFile("music.txt", String.format("%s,%s,%s,%s,%s,%s",
                    nameField.getText(), streetField.getText(), cityField.getText(),
                    stateField.getText(), zipField.getText(), musicType.getValue()));
        } else if (appCheckBox.isSelected()) {
            if (appTypeGroup.getSelectedToggle() == null) {
                showAlert("Please select an app type.");
                return;
            }
            if (isEmpty(titleField, "Title")) return;
            if (isEmpty(dateField, "Date Purchased")) return;
            if (isEmpty(accountField, "Account Number")) return;

            RadioButton selectedRadio = (RadioButton) appTypeGroup.getSelectedToggle();
            writeToFile("app.txt", String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                    nameField.getText(), streetField.getText(), cityField.getText(),
                    stateField.getText(), zipField.getText(), selectedRadio.getText(),
                    titleField.getText(), dateField.getText(), accountField.getText()));
        }

        clearForm();
    }

    private boolean isEmpty(TextField field, String fieldName) {
        if (field.getText().trim().isEmpty()) {
            showAlert(fieldName + " is required.");
            field.requestFocus();
            return true;
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            showAlert("Failed to write to file: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameField.clear(); streetField.clear(); cityField.clear(); stateField.clear(); zipField.clear();
        appCheckBox.setSelected(false); musicCheckBox.setSelected(false);
        musicType.getSelectionModel().selectFirst();
        appTypeGroup.selectToggle(null);
        titleField.clear(); dateField.clear(); accountField.clear();
        updateUI();
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

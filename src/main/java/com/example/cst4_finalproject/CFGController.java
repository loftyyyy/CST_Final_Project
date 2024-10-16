package com.example.cst4_finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CFGController {
    @FXML
    private Button exitButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField inputField;
    @FXML
    private TextArea outputField;

    @FXML
    private Label resultLabel;

    @FXML
    private Button startButton;

    private CFGLogic logic;

    @FXML
    public void initialize() {
        this.logic = new CFGLogic(outputField);
    }

    @FXML
    void clearField(ActionEvent event) {
        inputField.clear();
        outputField.clear();
        resultLabel.setText("");
    }

  @FXML
void startSimulation(ActionEvent event) {
    String input = inputField.getText();
    StringBuilder sb = new StringBuilder(input);
//    String modifiedInput = addCMiddle(input);
    sb.append("c").append(new StringBuilder(input).reverse().toString());
    boolean isValid = logic.isValidCFG(sb.toString());
    logic.displayResult(isValid);
    resultLabel.setText(isValid ? "Valid" : "Invalid");
}

private String addCMiddle(String input) {
    int middle = input.length() / 2;
    return input.substring(0, middle) + 'c' + input.substring(middle);
}
    public void gotoHome  (ActionEvent event ) throws IOException {

        if (event.getSource() == exitButton) {
            Stage window = (Stage) exitButton.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
}
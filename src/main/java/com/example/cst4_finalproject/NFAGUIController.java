package com.example.cst4_finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NFAGUIController {

    @FXML
    private AnchorPane automatonPane;

    @FXML
    private Button clearButton;
    @FXML
    private Button exitButton;

    @FXML
    private Label currentInput;

    @FXML
    private TextField inputField;

    @FXML
    private  TextField resultLabel;

    @FXML
    private Button startButton;

    private Automaton automaton;
    private NFAVisualizer nfaVisualizer;

    @FXML
    public void initialize(){
        automaton = new Automaton("NFA");
        nfaVisualizer = new NFAVisualizer(automaton, automatonPane);

        startButton.setOnAction(event -> startSimulation());

        clearButton.setOnAction(event -> clearDiagram());
    }

    private void clearDiagram(){
        nfaVisualizer.clear(inputField, resultLabel, currentInput);

    }

    private void startSimulation() {
        String input = inputField.getText();
        nfaVisualizer.runSimulation(input, inputField, resultLabel, currentInput); // Pass input to visualizer for simulation
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

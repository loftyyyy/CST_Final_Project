package com.example.cst4_finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DFAGUIController {
    @FXML
    private Button exitButton;

    @FXML
    private TextField inputField;

    @FXML
    private Button startButton;

    @FXML
    private Button clearButton;

    @FXML
    private Pane automatonPane;

    @FXML
    private TextField resultLabel;

    @FXML
    private Label currentInput;

    private Automaton automaton; // The automaton model
    private DFAVisualizer DFAVisualizer;

    @FXML
    public void initialize() {
        automaton = new Automaton("DFA");
        DFAVisualizer = new DFAVisualizer(automaton, automatonPane);

        // Set button action
        startButton.setOnAction(event -> startSimulation());

        clearButton.setOnAction(event -> clearDiagram());
    }

    private void clearDiagram(){
        DFAVisualizer.clear(inputField, resultLabel, currentInput);

    }

    private void startSimulation() {
        String input = inputField.getText();
        DFAVisualizer.runSimulation(input, inputField, resultLabel, currentInput); // Pass input to visualizer for simulation
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

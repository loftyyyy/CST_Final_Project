package com.example.cst4_finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TuringMachineAdditionController {
    @FXML
    private Button exitButton;
    @FXML
    private HBox TapeCells;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField inputField1;
    @FXML
    private TextField inputField2;

    @FXML
    private Button startButton;

    @FXML
    private Label resultLabel;

    @FXML
    private Pane diagramPane;
    private TuringMachineLogic turingMachineLogic;
    private Diagram stateDiagram;

    @FXML
    public void initialize() {
        stateDiagram = new Diagram();
        turingMachineLogic = new TuringMachineLogic(TapeCells, scrollPane, diagramPane, stateDiagram, resultLabel);

    }

    @FXML
    public void generateTape() {
        turingMachineLogic.createInputTape(inputField1.getText(), inputField2.getText());
    }

    @FXML
    public void startAutomaticMovement() {
        turingMachineLogic.startAutomaticCursorMovement();
    }

    @FXML
    public void stopAutomaticMovement() {
        turingMachineLogic.stopAutomaticCursorMovement();
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

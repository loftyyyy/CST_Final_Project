package com.example.cst4_finalproject;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDAGUIController{

    @FXML
    private Label L;

    @FXML
    private Label L1;

    @FXML
    private Label L2;

    @FXML
    private Label L3;

    @FXML
    private Label L4;

    @FXML
    private Label L5;

    @FXML
    private Label L6;

    @FXML
    private Label L7;

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
    private TextField resultLabel;

    @FXML
    private Button startButton;


    private Automaton automaton;
    private PDAVisualizer pdaVisualizer;

    private List<Label> stackLabel;

    @FXML
    public void initialize(){
        automaton = new Automaton("PDA");
        stackLabel = new ArrayList<>();
        stackLabel.addAll(List.of(L,L1,L2,L3,L4,L5,L6,L7));
//        stackLabel.add(L);
//        stackLabel.add(L1);
//        stackLabel.add(L2);
//        stackLabel.add(L3);
//        stackLabel.add(L4);
//        stackLabel.add(L5);
//        stackLabel.add(L6);
//        stackLabel.add(L7);

        pdaVisualizer = new PDAVisualizer(automaton, automatonPane,stackLabel);

        startButton.setOnAction(event -> {
            pdaVisualizer.runSimulation(inputField.getText(), inputField, resultLabel,currentInput);
        });

        clearButton.setOnAction(actionEvent -> {pdaVisualizer.clear(inputField,resultLabel,currentInput);});

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
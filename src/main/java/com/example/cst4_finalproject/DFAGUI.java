package com.example.cst4_finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DFAGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DFASimulator.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Automata Simulator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    // 0011001000

    public static void main(String[] args) {
        launch(args);
    }
}

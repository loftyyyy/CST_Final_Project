package com.example.cst4_finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class SVGLogicController extends Application {
    private ImageView Layer_1; // Ensure this matches the fx:id in FXML

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StartUp.fxml")); // Path to your FXML file
        primaryStage.setTitle("SVG Example");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

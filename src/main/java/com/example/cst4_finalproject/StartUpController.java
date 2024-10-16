package com.example.cst4_finalproject;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartUpController implements Initializable {

    // The @FXML annotation links this variable to the button defined in the FXML file
    @FXML
    private JFXButton myButton;


    @FXML
    private Text TITLE;

    @FXML
    private JFXButton StartButton;


    // Method linked to the button's onAction event
    public void start(ActionEvent event) throws IOException {

        if (event.getSource() == StartButton) {
            Stage window = (Stage) StartButton.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing StartUpController");
        System.out.println("TITLE: " + TITLE);
        System.out.println("StartButton: " + StartButton);

        if (TITLE == null) {
            System.out.println("TITLE is null. Check FXML configuration.");
        } else {
            System.out.println("TITLE is loaded successfully.");
            TITLE.setOpacity(0); // Start invisible
            FadeTransition fade = new FadeTransition();
            fade.setNode(TITLE);
            fade.setDuration(Duration.millis(2000));
            fade.setCycleCount(FadeTransition.INDEFINITE);
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setAutoReverse(true);
            fade.play();
        }

        if (StartButton != null) {
            StartButton.setOpacity(0); // Start invisible
            FadeTransition fading = new FadeTransition();
            fading.setNode(StartButton);
            fading.setDuration(Duration.millis(2000));
            fading.setCycleCount(FadeTransition.INDEFINITE);
            fading.setInterpolator(Interpolator.LINEAR);
            fading.setFromValue(0);
            fading.setToValue(1);
            fading.setAutoReverse(true);
            fading.play();
        } else {
            System.out.println("StartButton is null. Check FXML configuration.");
        }
    }
}

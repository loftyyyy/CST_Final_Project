package com.example.cst4_finalproject;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartUpCont extends Application implements Initializable {

    @FXML
    private JFXButton StartButton;

    @FXML
    private Text TITLE;

    //DFA
    //NDFA
    //Hanoi
    //CFG
    //PDA
    //Turing

    @Override
    public void start(Stage stage) throws IOException {
        // Load fonts
        Font.loadFont(getClass().getResource("/fonts/TekturNarrow-Black.ttf").toExternalForm(), -1);
        Font.loadFont(getClass().getResource("/fonts/TekturNarrow-Bold.ttf").toExternalForm(), -1);
        Font.loadFont(getClass().getResource("/fonts/TekturNarrow-ExtraBold.ttf").toExternalForm(), -1);
        Font.loadFont(getClass().getResource("/fonts/TekturNarrow-Medium.ttf").toExternalForm(), -1);
        Font.loadFont(getClass().getResource("/fonts/TekturNarrow-Regular.ttf").toExternalForm(), -1);
        Font.loadFont(getClass().getResource("/fonts/TekturNarrow-SemiBold.ttf").toExternalForm(), -1);

        // Load FXML and set up stage
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/cst4_finalproject/StartUpPage.fxml"));
        stage.setTitle("StartUp");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public void handleStartButton(ActionEvent event) throws IOException {
        System.out.println("Start button pressed"); // Debugging statement
        if (event.getSource() == StartButton) {
            // Close the current stage
            Stage currentStage = (Stage) StartButton.getScene().getWindow();
            currentStage.close();

            // Load and open HomePage.fxml in a new stage
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/cst4_finalproject/HomePage.fxml")));
            newStage.setScene(new Scene(root));
            newStage.setTitle("Home Page");
            newStage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing StartUpController");

        if (TITLE == null) {
            System.out.println("TITLE is null. Check FXML configuration.");
        } else {
            // Set TITLE to fade in and out indefinitely
            TITLE.setOpacity(0);
            FadeTransition fade = new FadeTransition(Duration.millis(2000), TITLE);
            fade.setCycleCount(FadeTransition.INDEFINITE);
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setAutoReverse(true);
            fade.play();
        }

        if (StartButton == null) {
            System.out.println("StartButton is null. Check FXML configuration.");
        } else {
            // Set StartButton to fade in and out indefinitely
            StartButton.setOpacity(0);
            FadeTransition fading = new FadeTransition(Duration.millis(2000), StartButton);
            fading.setCycleCount(FadeTransition.INDEFINITE);
            fading.setInterpolator(Interpolator.LINEAR);
            fading.setFromValue(0);
            fading.setToValue(1);
            fading.setAutoReverse(true);
            fading.play();
        }
    }
}

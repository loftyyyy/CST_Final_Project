package com.example.cst4_finalproject;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TowersOfHanoiController implements Runnable {

    @FXML
    private AnchorPane drawingPane;
    @FXML
    private TextArea numOfMovesLabel;
    @FXML
    private Label presentMoveLabel;
    @FXML
    private TextField numOfDisksField;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;

    private int n;
    private int count = 1;
    private int[][] pegCapacity;
    private int[] h;
    private Rectangle[] disk;
    private Thread t;

    @FXML
    public void initialize() {
        Platform.runLater(this::draw);
    }

@FXML
protected void onClearButtonClick() {
    // Stop the running thread if it exists
    if (t != null && t.isAlive()) {
        t.interrupt();
        t = null;
    }

    // Clear the drawing pane
    drawingPane.getChildren().clear();
    draw();

    // Reset the state variables
    n = 0;
    count = 1;
    pegCapacity = null;
    h = null;
    disk = null;

    // Clear the labels
    numOfMovesLabel.setText("0");
    presentMoveLabel.setText("Present Move: ");

    // Clear the text field
    numOfDisksField.clear();
}
private void initializeDisks() {
        h = new int[3];
        pegCapacity = new int[3][n];
        disk = new Rectangle[n];

        h[0] = n;
        h[1] = 0;
        h[2] = 0;

        for (int i = 0; i < n; i++) {
            int diskWidth = 200 - i * 25;
            int xPosition = 250 + (260 * 0) + (15 / 2) - (diskWidth / 2) ; // Adjusted to the left by 5 pixels
            disk[i] = new Rectangle(xPosition, 475 - i * 25, diskWidth, 25);
            pegCapacity[0][i] = i; // pushing disk numbers into the first peg
        }

        t = new Thread(this);
    }

    @FXML
    protected void onStartButtonClick() {
        try {
            n = Integer.parseInt(numOfDisksField.getText());
            initializeDisks();
            drawDisk();
            // Add a slight pause before starting the transition
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // 2-second pause
                    t.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (NumberFormatException e) {
            numOfDisksField.setText("Invalid number");
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    @Override
    public void run() {
        Hanoi(n, 1, 3, 2);
    }

private void Hanoi(int diskCount, int from, int dest, int by) {
    if (Thread.currentThread().isInterrupted()) {
        return;
    }
    if (diskCount == 1) {
        int horDisplacement = 260;
        try {
            Thread.sleep(1000);

            // maintains number of disks in each peg
            pegCapacity[dest - 1][h[dest - 1]] = pegCapacity[from - 1][--h[from - 1]];

            if ((from == 1 && dest == 2) || (from == 3 && dest == 2))
                horDisplacement = horDisplacement;
            else if ((from == 1 && dest == 3) || (from == 2 && dest == 3))
                horDisplacement = horDisplacement * 2;
            else if ((from == 3 && dest == 1) || (from == 2 && dest == 1))
                horDisplacement = 0;

            int num = pegCapacity[dest - 1][h[dest - 1]++];

            disk[num].setLocation(150 + num * 12 + horDisplacement, 475 - (h[dest - 1] - 1) * 25);

            Platform.runLater(() -> {
                drawDisk();
                numOfMovesLabel.setText(Integer.toString(count++));
                presentMoveLabel.setText("Present Move: Disk " + (num + 1) + " moved from " + (char) (from + 64) + " → " + (char) (dest + 64));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        Hanoi(diskCount - 1, from, by, dest);
        Hanoi(1, from, dest, by);
        Hanoi(diskCount - 1, by, dest, from);
    }
}
    private void draw() {
        // Create the canvas and set its size based on the drawingPane dimensions
        Canvas canvas = new Canvas(drawingPane.getWidth(), drawingPane.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear any previous content in the drawingPane
        drawingPane.getChildren().clear();

        // Draw the three towers
        gc.setFill(Color.web("#001F3F"));
        for (int i = 0; i < 3; i++) {
            gc.fillRect(250 + i * 260, 200, 15, 300); // Towers
            gc.fillText("" + (char) (i + 65), 255 + i * 260, 190); // Labels A, B, C
        }

        // Draw the base line
        gc.setStroke(Color.BLACK);
        gc.strokeLine(100, 500, 950, 500);

        // Uncomment and adjust this section for drawing the disks
        // for (int i = 0; i < n; i++) {
        //     gc.setFill(Color.web("#56ffff"));
        //     gc.fillRoundRect(disk[i].x, disk[i].y, disk[i].width, disk[i].height, 10, 10);
        //     gc.setStroke(Color.BLACK);
        //     gc.strokeRoundRect(disk[i].x, disk[i].y, disk[i].width, disk[i].height, 10, 10);
        //     gc.fillText(" " + (i + 1), disk[i].x + 100 - i * 12, disk[i].y + 12);
        // }

        // Add the canvas to the drawingPane
        drawingPane.getChildren().add(canvas);
    }
    public void drawDisk(){
        // Clear the current canvas to avoid ghost disks remaining on the first peg
        drawingPane.getChildren().clear(); // Clears all children before redrawing

        // Create a new canvas and GraphicsContext for drawing the updated disk positions
        GraphicsContext gc = new Canvas(drawingPane.getWidth(), drawingPane.getHeight()).getGraphicsContext2D();

        // Redraw the pegs
        drawPegs(gc);

        // Now, draw the disks at their updated locations
        for (int i = 0; i < n; i++) {
            gc.setFill(Color.web("#56ffff"));
            gc.fillRoundRect(disk[i].x, disk[i].y, disk[i].width, disk[i].height, 10, 10);
            gc.setStroke(Color.BLACK);
            gc.strokeRoundRect(disk[i].x, disk[i].y, disk[i].width, disk[i].height, 10, 10);
            gc.fillText(" " + (i + 1), disk[i].x + 100 - i * 12, disk[i].y + 12);
        }

        drawingPane.getChildren().add(gc.getCanvas());
    }

    // Separate method to handle redrawing the pegs and labels
    private void drawPegs(GraphicsContext gc) {
        gc.setFill(Color.web("#001F3F"));
        for (int i = 0; i < 3; i++) {
            gc.fillRect(250 + i * 260, 200, 15, 300); // Pegs
            gc.fillText("" + (char) (i + 65), 255 + i * 260, 190); // Peg labels (A, B, C)
        }
        gc.setStroke(Color.BLACK);
        gc.strokeLine(100, 500, 950, 500); // Draw the base line
    }

    private static class Rectangle {
        int x, y, width, height;

        Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        void setLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }
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
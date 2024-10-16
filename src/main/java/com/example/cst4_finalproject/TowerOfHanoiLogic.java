package com.example.cst4_finalproject;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TowerOfHanoiLogic {
    private AnchorPane pane;
    private Stack<StackPane>[] towers;
    private Queue<Runnable> moveQueue;
    private int poleWidth = 10;
    private int poleHeight = 200;
    private int diskHeight = 20;
    private double startX;
    private double startY;
    private double requiredSpacing;
    private Label moveLabel;
    private int moveCount;

    public TowerOfHanoiLogic(AnchorPane pane) {
        this.pane = pane;
        initializePoles();
        initializeMoveLabel();
    }

    private void initializePoles() {
        int numberOfPoles = 3;
        double spacingFactor = 2.5;
        double paneWidth = pane.getPrefWidth();
        double paneHeight = pane.getPrefHeight();
        double totalDisksWidth = 10 * 20; // Example width of the largest disk
        requiredSpacing = (totalDisksWidth / (numberOfPoles - 1)) * spacingFactor;
        double totalPolesWidth = (numberOfPoles * poleWidth) + ((numberOfPoles - 1) * requiredSpacing);
        startX = (paneWidth - totalPolesWidth) / 2;
        startY = (paneHeight - poleHeight) / 2;

        String[] labels = {"A", "B", "C"};
        for (int i = 0; i < numberOfPoles; i++) {
            double x = startX + i * (poleWidth + requiredSpacing);
            Rectangle pole = new Rectangle(x, startY, poleWidth, poleHeight);
            pole.setFill(Color.BLACK);
            pane.getChildren().add(pole);

            Label label = new Label(labels[i]);
            label.setLayoutX(x);
            label.setLayoutY(startY - 20); // Position the label above the pole
            pane.getChildren().add(label);
        }

        towers = new Stack[numberOfPoles];
        for (int i = 0; i < numberOfPoles; i++) {
            towers[i] = new Stack<>();
        }

        moveQueue = new LinkedList<>();
    }

    private void initializeMoveLabel() {
        moveLabel = new Label("Moves: 0");
        moveLabel.setLayoutX(10);
        moveLabel.setLayoutY(10);
        pane.getChildren().add(moveLabel);
        moveCount = 0;
    }

    public void clearDisks() {
        pane.getChildren().removeIf(node -> node instanceof StackPane && ((StackPane) node).getChildren().get(0) instanceof Rectangle);
        for (Stack<StackPane> tower : towers) {
            tower.clear();
        }
        moveQueue.clear();
        moveCount = 0;
        moveLabel.setText("Moves: 0");
    }

    public void startSimulation(int numberOfDisks) {
        clearDisks();
        initializeDisks(numberOfDisks);
        solveHanoi(numberOfDisks, 0, 2, 1);
        executeMoves();
    }

    private void initializeDisks(int numberOfDisks) {
        for (int i = numberOfDisks; i > 0; i--) {
            double diskWidth = i * 20;
            double x = startX + (poleWidth - diskWidth) / 2;
            double y = startY + poleHeight - (numberOfDisks - i + 1) * diskHeight;
            Rectangle disk = new Rectangle(diskWidth, diskHeight);
            disk.setFill(Color.BLUE);

            // Set the arc width and height for rounded corners
            disk.setArcWidth(20);
            disk.setArcHeight(20);

            Label label = new Label(String.valueOf(numberOfDisks - i + 1));
            label.setTextFill(Color.WHITE);

            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(x);
            stackPane.setLayoutY(y);
            stackPane.getChildren().addAll(disk, label);

            pane.getChildren().add(stackPane);
            towers[0].push(stackPane);
        }
    }

    private void solveHanoi(int n, int from, int to, int aux) {
        if (n == 0) return;
        solveHanoi(n - 1, from, aux, to);
        moveQueue.add(() -> moveDisk(from, to));
        solveHanoi(n - 1, aux, to, from);
    }

    private void moveDisk(int from, int to) {
        if (towers[from].isEmpty()) {
            throw new IllegalStateException("Attempted to move a disk from an empty tower.");
        }

        StackPane disk = towers[from].pop();
        double diskWidth = ((Rectangle) disk.getChildren().get(0)).getWidth();

        // Calculate the correct x and y positions for the disk's final position
        double targetX = startX + to * (poleWidth + requiredSpacing) + (poleWidth - diskWidth) / 2;
        double targetY;

        if (towers[to].isEmpty()) {
            // If the target pole has no disks, place the disk at the base
            targetY = startY + poleHeight - diskHeight;
        } else {
            // If the target pole has disks, place the disk on top of the existing disks
            targetY = startY + poleHeight - (towers[to].size() + 1) * diskHeight;
        }

        // Use translate transition for moving the disk
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), disk);
        transition.setToX(targetX - disk.getLayoutX());  // Translate relative to current position
        transition.setToY(targetY - disk.getLayoutY());  // Translate relative to current position

        // Once the transition is done, push the disk into the destination tower
        transition.setOnFinished(event -> {
            towers[to].push(disk);
            moveCount++;
            moveLabel.setText("Moves: " + moveCount);
        });

        transition.play();
    }

    private void executeMoves() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        while (!moveQueue.isEmpty()) {
            Runnable move = moveQueue.poll();

            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
            pauseTransition.setOnFinished(event -> move.run());  // Execute the move after the pause
            sequentialTransition.getChildren().add(pauseTransition);
        }
        sequentialTransition.play();
    }
}
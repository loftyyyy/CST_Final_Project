package com.example.cst4_finalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TuringMachineLogic {
    private List<StackPane> tapeCells = new ArrayList<>();
    private String cellString = "";
    private int currentIndex = 0;
    private HBox tape;
    private boolean isValid = true;
    private ScrollPane scrollPane;
    private Timeline timeline;
    private Diagram stateDiagram;
    private TransitionLines transitionLines;
    private Pane diagram;
    private Label resultLabel;

    public TuringMachineLogic(HBox tape, ScrollPane scrollPane, Pane diagram, Diagram stateDiagram, Label resultLabel) {
        this.tape = tape;
        this.scrollPane = scrollPane;
        this.stateDiagram = stateDiagram;
        this.diagram = diagram;
        this.resultLabel = resultLabel;

        transitionLines = new TransitionLines(diagram, 30);

        // Adding states to the diagram
        this.diagram.getChildren().addAll(stateDiagram.getState("q0"),
                stateDiagram.getState("q1"),
                stateDiagram.getState("q2"),
                stateDiagram.getState("q3"),
                stateDiagram.getState("q4"));

        // Initial Line
        transitionLines.addInitialStateArrow(stateDiagram.getState("q0"));

        // Transition Lines
        transitionLines.addArrowBetweenStates(stateDiagram.getState("q0"), stateDiagram.getState("q1"), "0, 1 / R");
        transitionLines.addArrowBetweenStates(stateDiagram.getState("q1"), stateDiagram.getState("q2"), "B, B / L");
        transitionLines.addArrowBetweenStates(stateDiagram.getState("q2"), stateDiagram.getState("q3"), "1, B / L");
        transitionLines.addArrowBetweenStates(stateDiagram.getState("q3"), stateDiagram.getState("q4"), "B, B / R");

        // Loop Lines
        transitionLines.addLoopArrow(stateDiagram.getState("q0"), 30, 50, "1, 1 / R", 20000);
        transitionLines.addLoopArrow(stateDiagram.getState("q1"), 30, 50, "1, 1 / R", 20000);
        transitionLines.addLoopArrow(stateDiagram.getState("q3"), 30, 50, "1, 1 / L", 20000);
    }

    public void createInputTape(String input, String input2) {
        char[] inputChars = input.toCharArray();
        char[] input2Chars = input2.toCharArray();

        // Generate Empty Cells
        StackPane emptyCell = createTapeCell('B');
        cellString += "B";
        tapeCells.add(emptyCell);
        tape.getChildren().add(emptyCell);

        for (char s : inputChars) {
            cellString += Character.toString(s);
            StackPane cell = createTapeCell(s);
            tapeCells.add(cell);
            tape.getChildren().add(cell);
        }

        StackPane separator = createTapeCell('0');
        tapeCells.add(separator);
        cellString += "0";
        tape.getChildren().add(separator);

        for (char s : input2Chars) {
            StackPane cell = createTapeCell(s);
            cellString += Character.toString(s);
            tapeCells.add(cell);
            tape.getChildren().add(cell);
        }

        StackPane separator2 = createTapeCell('B');
        cellString += "B";
        tapeCells.add(separator2);
        tape.getChildren().add(separator2);

    }

    public StackPane createTapeCell(char symbol) {
        StackPane cell = new StackPane();
        Rectangle rect = new Rectangle(90, 90);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        rect.setStrokeType(StrokeType.OUTSIDE);
        rect.setStrokeWidth(1);
        Label label = new Label(Character.toString(symbol));
        cell.getChildren().addAll(rect, label);
        return cell;
    }

    public void startAutomaticCursorMovement() {
        runSimulation();
    }

    public void stopAutomaticCursorMovement() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public String getNextState(String input) {
        String currentState = stateDiagram.getCurrentState();
        String nextState = null;

        if (currentState.equals("q0")) {
            if (input.equals("B")) {
                nextState = "q0";  // Stay in q0 if there's a blank.
                moveCursorRight();
            } else if (input.equals("0")) {
                writeToCurrentCell('1', currentIndex);  // Write '1' if we encounter '0'.
                nextState = "q1";
                moveCursorRight();
            } else if (input.equals("1")) {
                nextState = "q0";  // Stay in q0 if '1' and move right.
                moveCursorRight();
            }
        } else if (currentState.equals("q1")) {
            if (input.equals("1")) {
                nextState = "q1";
                moveCursorRight();
            } else if (input.equals("B")) {
                nextState = "q2";  // Move to q2 if blank is encountered.
                moveCursorLeft();
            }
        } else if (currentState.equals("q2")) {
            if (input.equals("1")) {
                writeToCurrentCell('B', currentIndex);  // Replace '1' with 'B' when in q2.
                nextState = "q3";
                moveCursorLeft();
            } else if (input.equals("B")) {
                nextState = "q3";
                moveCursorLeft();
            }
        } else if (currentState.equals("q3")) {
            if (input.equals("1")) {
                nextState = "q3";
                moveCursorLeft();
            } else if (input.equals("B")) {
                nextState = "q4";  // Final state q4 when we hit a blank.
                long count = cellString.chars().filter(ch -> ch == '1').count();
                resultLabel.setText("Result: " + count);


            }
        }

        return nextState;
    }


    private void moveCursorLeft() {
        if (currentIndex > 0) {
            StackPane currentCell = tapeCells.get(currentIndex);
            Rectangle currentRect = (Rectangle) currentCell.getChildren().get(0);
            currentRect.setStroke(Color.BLACK);
            currentRect.setStrokeWidth(1);

            currentIndex--;

            StackPane newCurrentCell = tapeCells.get(currentIndex);
            Rectangle newCurrentRect = (Rectangle) newCurrentCell.getChildren().get(0);
            newCurrentRect.setStroke(Color.RED);
            newCurrentRect.setStrokeWidth(2);

            scrollToCurrentIndex();
        }
    }

    private void moveCursorRight() {
        if (currentIndex > 0) {
            StackPane prevCell = tapeCells.get(currentIndex - 1);
            Rectangle prevRect = (Rectangle) prevCell.getChildren().get(0);
            prevRect.setStroke(Color.BLACK);
            prevRect.setStrokeWidth(1);
        }

        StackPane currentCell = tapeCells.get(currentIndex);
        Rectangle currentRect = (Rectangle) currentCell.getChildren().get(0);
        currentRect.setStroke(Color.RED);
        currentRect.setStrokeWidth(2);

        // Check if the current cell is the last cell
        if (currentIndex == tapeCells.size() - 1) {
            // Replace the final cell value with "B", regardless of its current value
            writeToCurrentCell('B', currentIndex);

            // Stop the automatic cursor movement
            stopAutomaticCursorMovement();
        } else {
            currentIndex++;  // Continue moving cursor to the right
        }

        scrollToCurrentIndex();
    }


    private String readCurrentInput() {
        StackPane currentCell = tapeCells.get(currentIndex);
        Label currentLabel = (Label) currentCell.getChildren().get(1);
        return currentLabel.getText();
    }

    public void writeToCurrentCell(char newSymbol, int index) {
        Timeline delayTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            StackPane currentCell = tapeCells.get(index);
            Label currentLabel = (Label) currentCell.getChildren().get(1);
            currentLabel.setText(Character.toString(newSymbol));
            System.out.println("Wrote " + newSymbol + " to the current cell.");
        }));
        delayTimeline.setCycleCount(1);
        delayTimeline.play();
    }

    private void scrollToCurrentIndex() {
        double totalWidth = tape.getWidth();
        double cellWidth = 90;
        double scrollPos = (currentIndex * cellWidth) / totalWidth;
        scrollPane.setHvalue(scrollPos);
    }

    public void runSimulation() {
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();
        }

        timeline = new Timeline();
        stateDiagram.resetToStartState();

        // Set a maximum number of iterations to prevent infinite loops (in case of errors).
        int maxIterations = 100;  // Adjust this value as needed.

        for (int step = 0; step < maxIterations; step++) {
            KeyFrame frame = new KeyFrame(Duration.seconds(step + 1), event -> {
                if (isValid) {
                    // Read current input from the tape (instead of cellString).
                    String currentInput = readCurrentInput();

                    // Get the next state based on the current state and input.
                    String nextState = getNextState(currentInput);
                    System.out.println(nextState + " " + currentInput);

                    if (nextState != null) {
                        // Transition between states.
                        StateNode currentStateNode = stateDiagram.getState(stateDiagram.getCurrentState());
                        currentStateNode.resetColor(true);

                        stateDiagram.setCurrentState(nextState);
                        StateNode nextStateNode = stateDiagram.getState(nextState);
                        nextStateNode.highlight(true);
                    } else {
                        isValid = false;
                        timeline.stop();
                    }
                }
            });

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
    }

}

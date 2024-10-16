package com.example.cst4_finalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class NFAVisualizer {

    boolean isValid = true;
    private Automaton automaton;
    private Pane pane;
    private Timeline timeline;
    private TransitionLines transitionLines;

    public NFAVisualizer(Automaton automaton, Pane pane){
        this.automaton = automaton;
        this.pane = pane;
        transitionLines = new TransitionLines(this.pane, 30);

        pane.getChildren().addAll(automaton.getState("q0"), automaton.getState("q1"), automaton.getState("q2"), automaton.getState("q3"));
        // Transition Lines Between States
        transitionLines.addInitialStateArrow(automaton.getState("q0"));
        transitionLines.addArrowBetweenStates(automaton.getState("q0"), automaton.getState("q1"), "0");
        transitionLines.addArrowBetweenStates(automaton.getState("q0"), automaton.getState("q2"), "1");
        transitionLines.addArrowBetweenStates(automaton.getState("q1"), automaton.getState("q3"), "0");
        transitionLines.addArrowBetweenStates(automaton.getState("q2"), automaton.getState("q3"), "1");
        //CurvedAboveLines
//        transitionLines.addBelowCurvedArrowBetweenStates(automaton.getState("q2"), automaton.getState("q0"), 20,130,"0");
        // Loop Lines
        transitionLines.addLoopArrow(automaton.getState("q0"), 30, 50,  "0,1", 20000);
        transitionLines.addLoopArrow(automaton.getState("q3"), 30,50, "0,1", 20000);

    }

    public void clear(TextField inputField, TextField resultLabel, Label currentSymbol) {
        this.isValid = true;
        currentSymbol.setText("Current Input: ");
        inputField.setText("");
        resultLabel.setText("");

        automaton.resetToStartState(); // Reset to the start state

        // Reset the visual representation of the states (clear highlights)
        for (StateNode state : automaton.getAllStates()) {
            state.resetColor(true);  // Or use only one reset call
        }

        // Stop the current timeline if it's running
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear(); // Clear keyframes for new input
        }
    }

    public void runSimulation(String input, TextField textfield, TextField label, Label currentSymbol) {
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();  // Ensure previous timeline is cleared
        }

        timeline = new Timeline();
        automaton.resetToStartState();

        if (!textfield.getText().isEmpty()) {
//            if (!isValidInput(input)) {
//                label.setText("Invalid characters in input.");
//                return; // Stop simulation if input is invalid
//            }

            // Loop through the input string and animate state transitions
            for (int i = 0; i < input.length(); i++) {
                char currentInput = input.charAt(i);
                String nextInput = (i + 1 < input.length()) ? Character.toString(input.charAt(i + 1)) : null; // Get next input or null if last character

                int step = i;

                KeyFrame frame = new KeyFrame(Duration.seconds(step + 1), event -> {
                    if (isValid) {  // Check if input is still valid
                        currentSymbol.setText("Current Input: " + currentInput );
                        String nextState = automaton.getNextStateForNFA(Character.toString(currentInput), nextInput);

                        if (nextState != null) {
                            StateNode currentStateNode = automaton.getState(automaton.getCurrentState());
                            currentStateNode.resetColor(true); // Reset current state's color

                            automaton.setCurrentState(nextState); // Move to next state
                            StateNode nextStateNode = automaton.getState(nextState);
                            nextStateNode.highlight(true); // Highlight next state
                        } else {
                            label.setText("Invalid Input String");
                            isValid = false;  // Stop further processing
                            timeline.stop();
                        }
                    }
                });

                timeline.getKeyFrames().add(frame);
            }

            KeyFrame finalStep = new KeyFrame(Duration.seconds(input.length() + 1), event -> {
                if (isValid) {  // Only check if input is valid
                    if (automaton.getCurrentState().equals(automaton.getFinalState())) {
                        label.setText("String Accepted!");
                    } else {
                        label.setText("String Rejected.");
                    }
                }
            });

            timeline.getKeyFrames().add(finalStep);
            timeline.play();
        } else {
            label.setText("Invalid Input");
        }
    }
}

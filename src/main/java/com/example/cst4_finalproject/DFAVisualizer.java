package com.example.cst4_finalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DFAVisualizer {
    boolean isValid = true;
    private Automaton automaton;
    private Pane pane;
    private Timeline timeline;
    private TransitionLines transitionLines;

    public DFAVisualizer(Automaton automaton, Pane pane) {
        this.automaton = automaton;
        this.pane = pane;
        transitionLines = new TransitionLines(this.pane, 30);


        transitionLines.addInitialStateArrow(automaton.getState("q0"));

        // Add the automaton states and transitions to the Pane
        pane.getChildren().addAll(automaton.getState("q0"), automaton.getState("q1"), automaton.getState("q2"), automaton.getState("q3"), automaton.getState("q4"));
        transitionLines.addArrowBetweenStates(automaton.getState("q0"), automaton.getState("q1"), "1");
        transitionLines.addArrowBetweenStates(automaton.getState("q1"), automaton.getState("q2"), "0");
        transitionLines.addArrowBetweenStates(automaton.getState("q2"), automaton.getState("q3"), "0");
        transitionLines.addArrowBetweenStates(automaton.getState("q3"), automaton.getState("q4"), "0");


        // Above Curve
//        addCurvedArrowBetweenStates(automaton.getState("q0"), automaton.getState("q4"),20,-130);
        transitionLines.addCurvedArrowBetweenStates(automaton.getState("q2"), automaton.getState("q1"),20,-90, "1");
        transitionLines.addCurvedArrowBetweenStates(automaton.getState("q4"), automaton.getState("q1"),20,-90, "1");

        //Below Curve
        transitionLines.addBelowCurvedArrowBetweenStates(automaton.getState("q3"), automaton.getState("q1"),10,90, "1");
        transitionLines.addBelowCurvedArrowBetweenStates(automaton.getState("q4"), automaton.getState("q0"),20,130, "0,1");

        //Loop Curve
        transitionLines.addLoopArrow(automaton.getState("q0"),30,50, "0", 20000);
        transitionLines.addLoopArrow(automaton.getState("q1"),30,50, "1",20000);



//        // Add transitions visually (you can also load from the automaton model)
//        Transition transition1 = new Transition(150, 200, 250, 200);
//        Arrow arrow1 = new Arrow(150, 200, 250, 200);
//        pane.getChildren().addAll(transition1, arrow1);
//
//        Transition transition2 = new Transition(250, 200, 150, 200);
//        Arrow arrow2 = new Arrow(250, 200, 150, 200);
//        pane.getChildren().addAll(transition2, arrow2);
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
                int step = i;

                KeyFrame frame = new KeyFrame(Duration.seconds(step + 1), event -> {
                    if (isValid) {  // Check if input is still valid
                        currentSymbol.setText("Current Input: " + currentInput );
                        String nextState = automaton.getNextStateForDFA(Character.toString(currentInput));

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

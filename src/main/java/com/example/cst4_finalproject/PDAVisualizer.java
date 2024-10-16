package com.example.cst4_finalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class PDAVisualizer{

    boolean isValid = true;
    private Automaton automaton;
    private Pane pane;
    private Timeline timeline;
    private TransitionLines transitionLines;
    private List<Label> stackLabel;

    public PDAVisualizer(Automaton automaton, Pane pane, List<Label> stackLabel){
        this.stackLabel = stackLabel;
        this.automaton = automaton;
        this.pane = pane;
        this.transitionLines = new TransitionLines(pane, 30);

        pane.getChildren().addAll(automaton.getState("q0"), automaton.getState("q1"), automaton.getState("q2"), automaton.getState("q3"));

        //Initial Arrow
        transitionLines.addInitialStateArrow(automaton.getState("q0"));

        //Between States Lines
        transitionLines.addArrowBetweenStates(automaton.getState("q0"), automaton.getState("q1"), "ε, ε → Z₀");
        transitionLines.addArrowBetweenStates(automaton.getState("q1"), automaton.getState("q2"), "ε, ε → ε");
        transitionLines.addArrowBetweenStates(automaton.getState("q2"), automaton.getState("q3"), "ε, Z₀ → ε");

        //Loop Lines
        transitionLines.addLoopArrow(automaton.getState("q1"), 30,50, "a, ε → a\nb, ε → b", 2);
        transitionLines.addLoopArrow(automaton.getState("q2"), 30,50, "a, a → ε\nb, b → ε",2);
    }

    public String inputManipulation(String input){
        String epsilon = "ε";
        StringBuilder sb = new StringBuilder();

        int mid = input.length() / 2;
        String firstHalf = input.substring(0,mid);
        String lastHalf = input.substring(mid);

        sb.append(epsilon).append(firstHalf).append(epsilon).append(lastHalf).append(epsilon);

        String result = sb.toString();
        System.out.println(result);
        return sb.toString();
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

        for(Label l : stackLabel){
            l.setText("");
        }
        // Stop the current timeline if it's running
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear(); // Clear keyframes for new input
        }
    }
    public boolean checkEvenPalindrome(String evenPalindrome){
        StringBuilder sb = new StringBuilder(evenPalindrome);
        if(!evenPalindrome.isEmpty() || !evenPalindrome.isBlank()){
            if(evenPalindrome.length() % 2 == 0){
                if(evenPalindrome.equals(sb.reverse().toString())){
                    return true;
                }


            }
        }
        return false;


    }

    public void runSimulation(String input,TextField textfield, TextField label, Label currentSymbol){
        if(checkEvenPalindrome(input)){
            input = inputManipulation(input);
            System.out.println("Yes it is a palindrome");

            if (timeline != null) {
                timeline.stop();
                timeline.getKeyFrames().clear();  // Ensure previous timeline is cleared
            }

            timeline = new Timeline();
            automaton.resetToStartState();

            if (!textfield.getText().isEmpty()) {
                automaton.setStackLabel(stackLabel);

                // Loop through the input string and animate state transitions
                for (int i = 0; i < input.length(); i++) {
                    char currentInput = input.charAt(i);
                    String nextInput = (i + 1 < input.length()) ? Character.toString(input.charAt(i + 1)) : null; // Get next input or null if last character

                    int step = i;

                    KeyFrame frame = new KeyFrame(Duration.seconds(step + 1), event -> {
                        if (isValid) {  // Check if input is still valid
                            currentSymbol.setText("Current Input: " + currentInput );
                            String nextState = automaton.getNextStateForPDA(Character.toString(currentInput));
                            System.out.println(currentInput + " -> " + nextState);

                            if (nextState != null) {
                                StateNode currentStateNode = automaton.getState(automaton.getCurrentState());
                                currentStateNode.resetColor(true); // Reset current state's color

                                automaton.setCurrentState(nextState); // Move to next state
                                StateNode nextStateNode = automaton.getState(nextState);
                                nextStateNode.highlight(true); // Highlight next state
                            } else {
                                if(automaton.checkIfStackEmpty()){
                                    label.setText("String is accepted");

                                }else{
                                    label.setText("Invalid Input String");

                                }
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
        }else{
            label.setText("String is not a valid palindrome");
        }

    }


}
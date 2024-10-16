package com.example.cst4_finalproject;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Automaton {
    private Map<String, StateNode> states = new HashMap<>();
    private String currentState;

    private List<Label> stackLabel;

    private Label topStackLabel;
    private String finalState = "q4";

    // Constructor to initialize the automaton
    public Automaton(String automaton) {
        if(automaton.equals("DFA")){
            // Add states and transitions for dfa
            states.put("q0", new StateNode(false, "q0", 0, 200));
            states.put("q1", new StateNode(false, "q1", 150, 200));
            states.put("q2", new StateNode(false, "q2", 300, 200));
            states.put("q3", new StateNode(false, "q3", 450, 200));
            states.put("q4", new StateNode(true, "q4", 600, 200));

            // Add states and transitions for nfa
        }else if(automaton.equals("NFA")){
            states.put("q0", new StateNode(false, "q0", 186.45, 169.5));
            states.put("q1", new StateNode(false, "q1", 339.0, 16.95));
            states.put("q2", new StateNode(false, "q2", 339.0, 322.05));
            states.put("q3", new StateNode(true, "q3", 491.55, 169.5));
        }else if(automaton.equals("PDA")){
            states.put("q0", new StateNode(false, "q0", 145.2, 204));
            states.put("q1", new StateNode(false, "q1", 320.4, 204));
            states.put("q2", new StateNode(false, "q2", 495.6, 204));
            states.put("q3", new StateNode(true, "q3", 670.8, 204));

        }

        currentState = "q0"; // Starting state
    }

    public StateNode getState(String stateName) {
        return states.get(stateName);
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String state) {
        currentState = state;
    }

    public String getFinalState(){
        return finalState;
    }

    public void resetToStartState(){
        setCurrentState("q0");

    }


    public List<StateNode> getAllStates(){
        return new ArrayList<>(states.values());



    }

    public void setStackLabel(List<Label> stackLabel){
        this.stackLabel = stackLabel;
    }
    public void addLabelToStack(String symbol) {
            for (Label currentLabel : this.stackLabel) {
                if (currentLabel.getText().isEmpty()) {
                    // Push the symbol onto the first empty label in the stack
                    this.topStackLabel = currentLabel;
                    currentLabel.setText(symbol);
                    break;  // Stop after adding one symbol
                }
            }
    }

    public void popLabelFromStack(String symbol) {
        // Check the top of the stack (last non-empty label)
        for (int i = stackLabel.size() - 1; i >= 0; i--) {
            Label currentLabel = stackLabel.get(i);
            if (!currentLabel.getText().isEmpty()) {
                if (currentLabel.getText().equals(symbol)) {
                    // If the top matches the symbol, pop it (clear the label)
                    currentLabel.setText("");
                    break;
                } else {
                    // Invalid string input, since the top symbol does not match
                    System.out.println("Invalid string input " + symbol +   ". Expected symbol: " + currentLabel.getText());
                    break;
                }
            }
        }
    }


    public boolean checkIfStackEmpty(){
        for(Label currentLabel : this.stackLabel){
            if(!currentLabel.getText().isEmpty()){
                return false;
            }
        }
        return true;
    }

    // Define transition rules for DFA/NFA
    public String getNextStateForDFA(String input) {
        this.finalState = "q4";

        // Transition logic
        if (currentState.equals("q0") && input.equals("0")) {
            return "q0";
        } else if (currentState.equals("q0") && input.equals("1")) {
            return "q1";
        }else if(currentState.equals("q1") && input.equals("0")){
            return "q2";
        }else if(currentState.equals("q1") && input.equals("1")){
            return "q1";
        }else if(currentState.equals("q2") && input.equals("0")){
            return "q3";
        }else if(currentState.equals("q2") && input.equals("1")){
            return "q1";
        }else if(currentState.equals("q3") && input.equals("0")){
            return "q4";
        }else if(currentState.equals("q3") && input.equals("1")){
            return "q1";
        }else if(currentState.equals("q4") && input.equals("0")){
            return "q0";
        }else if(currentState.equals("q4") && input.equals("1")){
            return "q1";
        }
        return null; // If no valid transition, return null
    }

    public String getNextStateForNFA(String input, String nextInput) {
        this.finalState = "q3";

        // If nextInput is null, handle it as the end of the input string
        if (nextInput == null) {
            System.out.println("End of input, processing the final character.");

            // Continue with normal state transitions based only on current input
            if (currentState.equals("q0")) {
                if (input.equals("0")) {
                    System.out.println("Transition from q0 to q1 on input '0'");
                    return "q1"; // Transition to q1 if the input is '0'
                } else if (input.equals("1")) {
                    System.out.println("Transition from q0 to q2 on input '1'");
                    return "q2"; // Transition to q2 if the input is '1'
                }
                return "q0"; // Default: loop back to q0
            }

            // Handle other states
            if (currentState.equals("q1") && input.equals("0")) {
                System.out.println("Transition from q1 to q3 on input '0'");
                return "q3"; // Transition to final state q3
            }

            if (currentState.equals("q1") && input.equals("1")) {
                return null; // Remain in q1 if input is '1'
            }

            if (currentState.equals("q2") && input.equals("1")) {
                System.out.println("Stay in q2 on input '" + input + "'");
                return "q3"; // Stay in q2 regardless of input
            }

            if (currentState.equals("q3") && input.equals("0")) {
                System.out.println("Stay in final state q3");
                return "q3"; // Remain in final state
            }
        }

        // Handle cases where nextInput is not null
        if (currentState.equals("q0")) {
            if (input.equals("0") && nextInput.equals("0")) {
                System.out.println("Transition from q0 to q1 on input '0'");
                return "q1"; // Transition to q1
            } else if (input.equals("1") && nextInput.equals("1")) {
                System.out.println("Transition from q0 to q2 on input '1'");
                return "q2"; // Transition to q2
            }
            System.out.println("Loop back to q0"); // Loop back to q0 if no match
            return "q0";
        }

        // Continue handling other states
        if (currentState.equals("q1") && input.equals("0")) {
            System.out.println("Transition from q1 to q3 on input '0'");
            return "q3"; // Transition to final state q3
        }


        if (currentState.equals("q1") && input.equals("1")) {
            System.out.println("Loop in q1 on input '1'");
            return "q1"; // Stay in q1 on input '1'
        }

        if (currentState.equals("q2") && input.equals("0")) {
            System.out.println("Stay in q2 on input '0'");
            return "q2"; // Stay in q2 on input '0'
        }

        if (currentState.equals("q2") && input.equals("1")) {
            System.out.println("Stay in q2 on input '1'");
            return "q3"; // Stay in q2 on input '1'
        }

        if (currentState.equals("q3")) {
            System.out.println("Stay in final state q3");
            return "q3"; // Remain in final state q3
        }

        return null; // Return null for invalid input
    }

    public String getNextStateForPDA(String input){
        this.finalState = "q3";


        if(currentState.equals("q0") && input.equals("ε")){
            //Add z₀ in the stack
            addLabelToStack("z₀");
            return "q1";
        }else if(currentState.equals("q1")){
            if(input.equals("a")){
                //Add 'a' in the stack
                addLabelToStack("a");
                return "q1";



            }else if(input.equals("b")){
                //Add 'b' in the stack
                addLabelToStack("b");
                return "q1";


            }else{
                return "q2";
            }
        }else if(currentState.equals("q2")){
            if(input.equals("a")){
                //Checks the top of the stack if it's a, and if it's it removes it from the stack. If not,  automatically halt the process and return a not a valid palindrome
                popLabelFromStack("a");
                return "q2";



            }else if(input.equals("b")){
                //Checks the top of the stack if it's b, and if it's it removes it from the stack. If not, automatically halt the process and return a not a valid palindrome
                popLabelFromStack("b");
                return "q2";


            }else if(input.equals("ε")){
                popLabelFromStack("z₀");
                return "q3";
            }

        }

        return null;

    }
}



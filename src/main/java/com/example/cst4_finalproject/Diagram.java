package com.example.cst4_finalproject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diagram {
    private Map<String, StateNode> states = new HashMap<>();
    private String currentState;


    public Diagram(){

        states.put("q0", new StateNode(false, "q0",0,198.5));
        states.put("q1", new StateNode(false, "q1", 217.5, 198.5));
        states.put("q2", new StateNode(false, "q2", 435, 198.5));
        states.put("q3", new StateNode(false, "q3", 652.5, 198.5));
        states.put("q4", new StateNode(true, "q4", 870, 198.5));

        this.currentState = "q0";

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


    public void resetToStartState(){
        setCurrentState("q0");

    }


    public List<StateNode> getAllStates(){
        return new ArrayList<>(states.values());


    }

}

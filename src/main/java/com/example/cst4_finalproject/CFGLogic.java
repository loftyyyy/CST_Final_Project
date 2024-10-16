package com.example.cst4_finalproject;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class CFGLogic {
    private TextArea outputField;
    private List<String> steps;

    public CFGLogic(TextArea outputField) {
        this.outputField = outputField;
        this.steps = new ArrayList<>();
    }

    public boolean isValidCFG(String input) {
        steps.clear();
        boolean isValid = checkCFG(input, 0, input.length() - 1);
        displayResult(isValid);
        return isValid;
    }

    private boolean checkCFG(String input, int start, int end) {
        if (start > end) {
            return false;
        }
        if (start == end) {
            if (input.charAt(start) == 'c') {
                steps.add("S → " + input);
                return true;
            }
            return false;
        }
        if (input.charAt(start) == 'a' && input.charAt(end) == 'a') {
            steps.add("S → " + input.substring(0, start) + "aSa" + input.substring(end + 1));
            return checkCFG(input, start + 1, end - 1);
        }
        if (input.charAt(start) == 'b' && input.charAt(end) == 'b') {
            steps.add("S → " + input.substring(0, start) + "bSb" + input.substring(end + 1));
            return checkCFG(input, start + 1, end - 1);
        }
        return false;
    }

    public void displayResult(boolean isValid) {
        StringBuilder result = new StringBuilder();
        if (isValid) {
            result.append("The input string is valid according to the CFG rules.\n\nDerivation steps:\n");
            for (String step : steps) {
                result.append(step).append("\n");
            }
        } else {
            result.append("The input string is not valid according to the CFG rules.");
        }
        outputField.setText(result.toString());
    }
}
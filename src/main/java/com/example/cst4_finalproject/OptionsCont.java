package com.example.cst4_finalproject;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsCont implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private JFXButton simulatecfg;
    @FXML
    private JFXButton simulatedfa;
    @FXML
    private JFXButton simulatendfa;
    @FXML
    private JFXButton simulatePDA;
    @FXML
    private JFXButton simulatetm;
    @FXML
    private JFXButton simulateth;
    @FXML
    private TextArea textArea;




    public void gotoCFG (ActionEvent event ) throws IOException {
        if (event.getSource() == simulatecfg) {
            Stage window = (Stage) simulatecfg.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CFGSimulator.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    public void gotoDFA  (ActionEvent event ) throws IOException {
        if (event.getSource() == simulatedfa) {
            Stage window = (Stage) simulatedfa.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DFASimulator.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    public void gotoNDFA  (ActionEvent event ) throws IOException {
        if (event.getSource() == simulatendfa) {
            Stage window = (Stage) simulatendfa.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NDFASimulator.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    public void gotoPDA  (ActionEvent event ) throws IOException {
        if (event.getSource() == simulatePDA) {
            Stage window = (Stage) simulatePDA.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PDA.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    public void gotoTM  (ActionEvent event ) throws IOException {

        if (event.getSource() == simulatetm) {
            Stage window = (Stage) simulatetm.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Operation.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    public void gotoTH (ActionEvent event ) throws IOException {
        if (event.getSource() == simulateth) {
            Stage window = (Stage) simulateth.getScene().getWindow();
            window.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TowerofHanoi.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    public void CFGText(){
        textArea.setStyle("-fx-text-fill: white;"); // Changes the text color to blue
        textArea.setText("");
        textArea.setFont(new Font ("Tektur Narrow Regular", 35));
        textArea.setText("Context-Free Grammar (CFG) is a formal grammar that describes the syntax of languages where each production rule applies to a single non-terminal symbol. It’s commonly used in programming languages and compiler design, where rules are defined to create valid strings within a language. In CFG, each rule is simple enough to be processed by a Pushdown Automaton, allowing efficient parsing of hierarchical structures, like arithmetic expressions.");
    }

    public void DFAText(){
        textArea.setStyle("-fx-text-fill: white;"); // Changes the text color to blue
        textArea.setFont(new Font ("Tektur Narrow Regular", 35));
        textArea.setText("");
        textArea.setText("A Deterministic Finite Automaton (DFA) is a theoretical machine used to recognize patterns or \"accept\" certain strings within a language. In DFA, for each state and input, there is a unique next state, making it deterministic. It's widely used in applications like lexical analysis, where the DFA checks if the input matches a specific set of rules. DFAs are also efficient and useful for simple pattern matching due to their structured and finite state transitions.");
    }

    public void NFAText(){
        textArea.setStyle("-fx-text-fill: white;"); // Changes the text color to blue
        textArea.setFont(new Font ("Tektur Narrow Regular", 35));
        textArea.setText("");
        textArea.setText("The Nondeterministic Finite Automaton (NFA) is similar to the DFA but allows multiple possible transitions for each state and input symbol, including transitions without input. NFAs are helpful for theoretical exploration, as they’re often easier to design. Although they might seem more complex, NFAs can be converted into an equivalent DFA. They’re foundational in language recognition and regex engines, handling flexible matching.");
    }

    public void PDAText(){
        textArea.setStyle("-fx-text-fill: white;"); // Changes the text color to blue
        textArea.setFont(new Font ("Tektur Narrow Regular", 35));
        textArea.setText("");
        textArea.setText("A Pushdown Automaton (PDA) is an abstract machine that extends the capabilities of a finite automaton by adding a stack for memory. This structure enables PDAs to recognize context-free languages, such as balanced parentheses or nested expressions. The stack memory allows it to keep track of previous states, making PDAs essential for parsing applications where hierarchical or nested structures are analyzed, like in programming language syntax parsing.");
    }
    public void TowerOfHanoiText(){
        textArea.setStyle("-fx-text-fill: white;"); // Changes the text color to blue
        textArea.setFont(new Font ("Tektur Narrow Regular", 35));
        textArea.setText("");
        textArea.setText("The Tower of Hanoi is a classic puzzle that involves moving a stack of disks from one peg to another, following specific rules. Only one disk can be moved at a time, and a larger disk cannot be placed on a smaller one. It’s a great demonstration of recursion, a fundamental concept in computer science, where each move builds on the previous one. Solving the puzzle involves breaking down the problem into smaller steps, making it a favorite example in teaching algorithms.");
    }
    public void TuringMachine(){
        textArea.setStyle("-fx-text-fill: white;"); // Changes the text color to blue
        textArea.setFont(new Font ("Tektur Narrow Regular", 35));
        textArea.setText("");
        textArea.setText("A Turing Machine is a theoretical computational model that manipulates symbols on an infinite tape according to a set of rules. Despite its simplicity, the Turing Machine is powerful enough to simulate any algorithm and is central to the theory of computation. It forms the basis of the concept of “computability,” distinguishing between problems that can be solved by computers and those that cannot. The Turing Machine’s versatility and simplicity make it a cornerstone of computer science.");
    }
}

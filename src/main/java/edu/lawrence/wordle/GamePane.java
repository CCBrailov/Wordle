package edu.lawrence.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GamePane extends Pane {
    
    public static Set<String> dictionary;
    
    private static final Font font1 = new Font("Arial", 50);
    private static final Font font2 = new Font("Arial", 20);
    
    private final Text endText1;
    private final Text endText2;
    private final Text endText3;
    
    private final GameBoard board;
    private final Keyboard keyboard;
    
    private Session session; //Session object controls most game functions
    
    public GamePane() {
        
        //Initialize dictionary and color palette
        GamePane.Init();
        Colors.Init();
        
        //Create game board, keyboard, and session objects
        board = new GameBoard();
        keyboard = new Keyboard();
        session = new Session(this);
        
        //Create blank endgame text objects
        endText1 = new Text("");
        endText2 = new Text("");
        endText3 = new Text("");
        
        //Setup endgame text and draw board + keyboard
        initEndText();
        draw();
        
        //Set up the click handler
        this.setOnMouseClicked(e -> gameplayClickHandler(e));
        
    }
    
    private void initEndText() {

        endText1.setFont(font1);
        endText1.setStroke(null);
        endText1.setFill(Color.RED);
        
        endText2.setFont(font2);
        endText2.setStroke(null);
        endText2.setFill(Color.RED);
        
        endText3.setFont(font2);
        endText3.setStroke(null);
        endText3.setFill(Color.RED);
        
    }
    
    private void positionEndText() {
        
        //Center-align text
        endText1.setX((this.getWidth() -
                endText1.getLayoutBounds().getWidth()) / 2);
        endText2.setX((this.getWidth() -
                endText2.getLayoutBounds().getWidth()) / 2);
        endText3.setX((this.getWidth() -
                endText3.getLayoutBounds().getWidth()) / 2);
        
        //Set Y positions for text
        endText1.setY(100);
        endText2.setY(endText1.getY() +
                endText2.getLayoutBounds().getHeight());
        endText3.setY(endText2.getY() +
                endText3.getLayoutBounds().getHeight());
        
    }
    
    private void clearText() {
        
        endText1.setText("");
        endText2.setText("");
        endText3.setText("");
        
    }
    
    private void gameplayClickHandler(MouseEvent e) {
        
        //Get mouse position
        int mouseX = (int) e.getX();
        int mouseY = (int) e.getY();
        
        //Get key at mouse position (returns null if no key)
        String s = keyboard.keyAtPosition(mouseX, mouseY);
        
        if (s != null) { keyClicked(s); }
        
    }
    
    private void endgameClickHandler(MouseEvent m) {
        restart();
        this.setOnMouseClicked(e -> gameplayClickHandler(e));
    }
    
    private void keyClicked(String s) {
        
        //Unique actions for "Enter" and "Backspace" keys, else add letter
        switch (s) {
            case "Enter":
                session.submit();
                draw();
                break;
            case "\u25C4": //Unicode for Backspace arrow charatcer
                session.backspace();
                draw();
                break;
            default:
                session.addLetter(s);
                draw();
                break;
        }
        
    }
    
    private void draw() {
        
        //Clear everything
        this.getChildren().clear();
        
        //Show the cells
        Row[] rows = board.getRows();
        for (Row r : rows) {
            Cell[] cells = r.getCells();
            for (Cell c : cells) {
                this.getChildren().addAll(c.getShapes());
            }
        }
        
        //Show the keyboard;
        this.getChildren().addAll(keyboard.getKeys());
        
        //Show the endgame text (empty unless gameState() has been called)
        this.getChildren().add(endText1);
        this.getChildren().add(endText2);
        this.getChildren().add(endText3);
        
    }
    
    public void gameState(String s) {
        
        //Changes the click handling so that any click restarts the game
        this.setOnMouseClicked(e -> endgameClickHandler(e));
        
        //The middle text is always the same regardless of win/lose
        endText2.setText("The secret word was \"" + 
                session.getSecretWord() + ".\"");
        
        //Change the first/third text lines depending on win or lose
        switch(s) {
            case "win":
                endText1.setText("You Win!");
                endText3.setText("Click anywhere to play again.");
                positionEndText();
                break;
            case "lose":
                endText1.setText("Game Over.");
                endText3.setText("Click anywhere to try again.");
                positionEndText();
                break;
        }
        
    }
    
    public void restart() {
        session = new Session(this);
        draw();
        clearText();
    }
    
    public static void Init() {
        
        //Load in the dictionary from the file and only include 5-letter words
        dictionary = new TreeSet<String>();
        try {
            Scanner input = new Scanner(new File("words.txt"));
            while(input.hasNext()) {
                String str = input.next();
                if (str.length() == 5) { dictionary.add(str); }
            }
        } catch(FileNotFoundException ex) {
            System.out.println("Dictionary file not found");
        }
        
    }
    
    public GameBoard getBoard() { return board; }
    
    public Keyboard getKeyboard() { return keyboard; }
    
}
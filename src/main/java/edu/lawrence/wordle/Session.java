package edu.lawrence.wordle;

import static edu.lawrence.wordle.Colors.*;
import static edu.lawrence.wordle.GamePane.dictionary;

import java.util.Random;
import java.util.ArrayList;

public class Session {
    
    private final GamePane pane;
    private final GameBoard board;
    
    private final String secretWord;
    private final String[] secretLetters;
    
    private int round;
    private Row activeRow;
    
    public Session(GamePane g){
        
        //Set pane and board
        pane = g;
        board = g.getBoard();
        
        //Set or reset the game
        board.clear();
        g.getKeyboard().resetKeys();
        round = 0;
        activeRow = board.getRows()[round];
        
        //Get a random word from the dictionary
        Random rand = new Random();
        int r = rand.nextInt(dictionary.size());
        ArrayList<String> loader = new ArrayList<>(dictionary);
        secretWord = loader.get(r);
        
        //NOTE: This line is kept in for testing purposes, as the dictionary is
        //extremely broad, which makes the game frequently unwinnable compared
        //to the official Wordle.
        System.out.println("Secret Word: " + secretWord);
        
        //Load the letters of the secret word into an array
        secretLetters = new String[5];
        for (int i = 0; i < 5; i++) {
            secretLetters[i] = secretWord.substring(i, i + 1);
        }
        
        
    }

    public void addLetter(String s) {
        
        Cell[] cells = activeRow.getCells();
        
        //Find the next empty cell and add the letter (does nothing if there are
        //letters in all five cells)
        for (Cell c : cells) {
            if ("".equals(c.getLetter())) {
                c.setLetter(s);
                break;
            }
        }
    }

    public void backspace() {
        
        Cell[] cells = activeRow.getCells();
        
        //Do nothing and break if the first cell is empty
        if ("".equals(cells[0].getLetter())) { return; }
        
        //If the last cell is filled, make it empty, otherwise find the first
        //empty cell and empty the previous cell.
        if (!"".equals(cells[4].getLetter())) { cells[4].setLetter(""); }
        else {
            for (int i = 0; i < cells.length; i++) {
                if ("".equals(cells[i].getLetter())) {
                    cells[i - 1].setLetter("");
                }
            }
        }
    }

    public void submit() {
        
        //Exit immediately if the row is not full
        if (!activeRow.filled()) { return; }
        
        //Load the word into an array of letters and a word object
        String guessWord = activeRow.getWord().toLowerCase();
        String[] guessLetters = activeRow.getWordArray();
        Cell[] cells = activeRow.getCells();
        
        //Exit if the word is not in the dictionary
        if (!dictionary.contains(guessWord)) { return; }
        
        //Color the letters based on Wordle rules
        for (int i = 0; i < 5; i++) {
            Cell c = cells[i];
            if (guessLetters[i].equals(secretLetters[i])) {
                c.setGreen();
                pane.getKeyboard().colorKey(guessLetters[i], green);
            }
            else if (arraySearch(guessLetters[i], secretLetters)) {
                c.setYellow();
                pane.getKeyboard().colorKey(guessLetters[i], yellow);
            }
            else { 
                c.setDark();
                pane.getKeyboard().colorKey(guessLetters[i], dGray);
            }
        }
        
        //Exit if player has won
        if (guessWord.equals(secretWord)) {
            pane.gameState("win");
            return;
        }
        
        //Increment round and exit if player has lost
        round += 1;
        if (round >= 6) {
            pane.gameState("lose");
            return;
        }
        
        //Move to next row
        activeRow = board.getRows()[round];
    }
    
    public String getSecretWord() { return secretWord; }
    
    //This method just exists to make the submit() method a little cleaner. It
    //checks to see if a String is anywhere inside an array of strings, used to
    //tell when a cell should be yellow.
    private boolean arraySearch(String s, String[] a) {
        for (String str : a) {
            if (s.equals(str)) { return true; }
        }
        return false;
    }
    
}

package edu.lawrence.wordle;

import static edu.lawrence.wordle.Colors.*;
import static edu.lawrence.wordle.GameBoard.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Cell {
    
    private static final Font font = new Font("Arial", 30);
    
    private final Shape[] shapes;
    private final Rectangle rect;
    private final Text letter;
    
    private final int colNum;
    private final int rowNum;
    private final int xPos;
    private final int yPos;
    
    Cell(Row r, int i) {
        
        //Set row and column
        colNum = i;
        rowNum = r.getRowNum();
        
        //Calculate X and Y coordinates
        xPos = xMarg + (colNum * (cellSize + gap));
        yPos = yMarg + (rowNum * (cellSize + gap));
        
        //Create shapes
        rect = new Rectangle(xPos, yPos, cellSize, cellSize);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        letter = new Text();
        letter.setFill(black);
        letter.setStroke(null);
        
        //Create and fill shapes array
        shapes = new Shape[2];
        shapes[0] = rect;
        shapes[1] = letter;
        
        //Set up text shape for letter
        letter.setFont(font);
        letter.setY(yPos + (cellSize / 2) + 9); //X position is set by setLetter
        
        //Set cell as empty
        clear();
        
    }
    
    public void clear() {
        
        //Set text as empty
        letter.setText("");
        
        //Set rectangle as empty with grey outline
        rect.setFill(null);
        rect.setStrokeWidth(3);
        rect.setStroke(lGray);
        
    }
    
    public void setLetter(String s) {
        
        letter.setText(s);
        
        //Center text
        int w = (int) letter.getLayoutBounds().getWidth();
        letter.setX(xPos + (cellSize - w) / 2);
    }
    
    public void setGreen() {
        rect.setFill(green);
    }
    
    public void setYellow() {
        rect.setFill(yellow);
    }
    
    public void setDark() {
        rect.setFill(dGray);
    }
    
    public String getLetter() {
        return letter.getText().toLowerCase();
    }
    
    public Shape[] getShapes() {
        return shapes;
    }
    
}

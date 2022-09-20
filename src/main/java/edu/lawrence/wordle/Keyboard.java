package edu.lawrence.wordle;

import static edu.lawrence.wordle.Colors.*;
import static edu.lawrence.wordle.GameBoard.*;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Keyboard {
    
    private static final Font font = new Font("Arial", 14);
    
    private Key[][] rows;
    private Key[] row0;
    private Key[] row1;
    private Key[] row2;
    
    public Keyboard() {
        
        //Create keys QWERTYUIOP
        row0 = new Key[10];
        String row0keys = "QWERTYUIOP";
        for (int i = 0; i < 10; i++) {
            row0[i] = new Key(i, 0, 0);
            row0[i].text.setText(String.valueOf(row0keys.charAt(i)));
            row0[i].centerText();
        }
        
        //Create keys ASDFGHJKL
        row1 = new Key[9];
        String row1keys = "ASDFGHJKL";
        for (int i = 0; i < 9; i++) {
            row1[i] = new Key(i, 1, 20);
            row1[i].text.setText(String.valueOf(row1keys.charAt(i)));
            row1[i].centerText();
        }
        
        //Create keys ZXCVBNM and placeholder keys for Enter and Backspace
        row2 = new Key[9];
        String row2keys = "-ZXCVBNM-";
        for (int i = 0; i < 9; i++) {
            row2[i] = new Key(i, 2, 0);
            row2[i].text.setText(String.valueOf(row2keys.charAt(i)));
            row2[i].centerText();
        }
        
        //Resize Enter key and add text
        row2[0].setSize(60);
        row2[0].setText("Enter");
        row2[0].centerText();
        
        //Resize Backspace key and add text
        row2[8].setSize(50);
        row2[8].setText("\u25C4");
        row2[8].centerText();
        
        //Shift ZXCVBNM + Backspace keys to make room for Enter key
        for (int i = 1; i < 9; i++) {
            row2[i].moveX(25);
        }
        
        //Add all rows into array of arrays for iterating
        rows = new Key[3][];
        rows[0] = row0;
        rows[1] = row1;
        rows[2] = row2;
        
    }
    
    public Shape[] getKeys() {
        
        //Create ArrayList for easy adding
        ArrayList<Shape> shapesList = new ArrayList<>();
        
        //Iterate through keyboard rows and add shapes from each key
        for (Key[] kr : rows) {
            for (Key k : kr) {
                Shape[] keyShapes = k.getShapes();
                shapesList.addAll(Arrays.asList(keyShapes));
            }
        }
        
        //Cast ArrayList to Array for return
        Shape[] shapes = new Shape[shapesList.size()];
        for (Shape s : shapesList) { shapes[shapesList.indexOf(s)] = s;}
        return shapes;
        
    }
    
    public String keyAtPosition(int x, int y) {
        
        //Iterate through all keys and return the first key that contains (x, y)
        for (Key[] kr : rows) {
            for (Key k : kr) {
                if (k.containsPoint(x, y)) { return k.getText();}
            }
        }
        
        //If no key found, return null
        return null;
        
    }
    
    public void colorKey(String s, Color c) {
        
        //Find corresponding key and change to corresponding color, unless key
        //is color-locked. If changing to green, lock the key so that green keys
        //cannot be turned yellow.
        for (Key[] row : rows) {
            for (Key k : row) {
                if (k.getText().toLowerCase().equals(s)) {
                    if (k.locked) { return; }
                    k.rect.setFill(c);
                    if (c == green) { k.lock(); }
                    return;
                }
            }
        }
        
    }
    
    public void resetKeys() {
        
        //Set keys back to default color and unlock all color-locked keys
        for (Key[] kr : rows) {
            for (Key k : kr) {
                k.rect.setFill(lGray);
                k.unlock();
            }
        }
        
    }
    
    private class Key {
        
        private final Shape[] shapes;
        private final Rectangle rect;
        private final Text text;
        
        private boolean locked;
        
        public Key() {
            
            //Create gray rounded rectangle with no position
            rect = new Rectangle();
            rect.setFill(lGray);
            rect.setArcHeight(15);
            rect.setArcWidth(15);
            
            //Create blank text object with no position
            text = new Text();
            text.setStroke(null);
            text.setFill(black);
            text.setFont(font);
            
            //Add rectangle and text into shapes array
            shapes = new Shape[2];
            shapes[0] = rect;
            shapes[1] = text;
            
            //Set key as not color-locked
            locked = false;
            
        }
        
        //i = number in row
        //r = row
        //o = x offset for start of row
        public Key(int i, int r, int o) {
            
            //Calculate size of board
            int boardSize = 50 + 6 * (cellSize + gap);
            
            //Calculate x and y position
            int xPos = o + (i * 40);
            int yPos = boardSize + (r * 50);
            
            //Create rounded rectangle at position
            rect = new Rectangle(xPos, yPos, 35, 45);
            rect.setFill(Colors.lGray);
            rect.setArcHeight(15);
            rect.setArcWidth(15);
            
            //Create blank text object at position
            text = new Text();
            text.setX(xPos + 13);
            text.setY(yPos + 25);
            text.setStroke(null);
            text.setFill(black);
            text.setFont(font);
            
            //Add rectangle and text into shapes array
            shapes = new Shape[2];
            shapes[0] = rect;
            shapes[1] = text;
            
            //Set key as not color-locked
            locked = false;
            
        }
        
        public void setSize(int i) { rect.setWidth(i); }
        
        public void centerText() {
            
            //Get dimensions and origin of rectangle
            int rx = (int) rect.getX();
            int ry = (int) rect.getY();
            int rw = (int) rect.getWidth();
            int rh = (int) rect.getHeight();
            
            //Get width and height of text
            int tw = (int) text.getLayoutBounds().getWidth();
            int th = (int) text.getLayoutBounds().getHeight();
            
            //Center text inside key
            text.setX(rx + ((rw - tw) / 2));
            text.setY(ry + rh - ((rh - th) / 2) - 3);
        }
        
        public void reset() {
            
            rect.setFill(Colors.lGray);
            unlock();
            
        }
        
        public void moveX(int i) {
            
            rect.setX(rect.getX() + i);
            text.setX(text.getX() + i);
            
        }

        public boolean containsPoint(int x, int y) { return rect.contains(x, y); }
        
        public void lock() { locked = true; }
        
        public void unlock() { locked = false; }
        
        public void setText(String s) { text.setText(s); }
        
        public String getText() { return text.getText(); }
        
        public Shape[] getShapes() { return shapes; }
    }
    
}

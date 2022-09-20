package edu.lawrence.wordle;

public class Row {
    
    private final Cell[] cells;
    private final int rowNum;
    
    public Row(int n) {
        
        rowNum = n;
        cells = new Cell[5];
        
        for (int i = 0; i < 5; i++) { cells[i] = new Cell(this, i);}   
        
    }
    
    public boolean filled() {
        
        //Iterates through cells and returns false if any are empty
        for (Cell c : cells) {
            if ("".equals(c.getLetter())) { return false; }
        }
        
        return true;
        
    }
    
    public Cell[] getCells() { return cells; }
    
    public int getRowNum() { return rowNum; }
    
    public String getWord() {
        
        //Gets the first letter
        String word = cells[0].getLetter();
        
        //Iterates through cells 2-5 and adds letters one-by-one
        for (int i = 1; i < 5; i++) { word += cells[i].getLetter(); }
        return word;
        
    }
    
    public String[] getWordArray() {
        
        String[] letters = new String[5];
        for (int i = 0; i < 5; i++) {
            letters[i] = cells[i].getLetter();
        }
        return letters;
        
    }
    
}

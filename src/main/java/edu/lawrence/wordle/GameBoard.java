package edu.lawrence.wordle;

public class GameBoard {
    
    //Dimensions of gameboard
    public static int yMarg = 0;        //Y margin
    public static int xMarg = 10;       //X margin
    public static int cellSize = 70;    //Size of cells
    public static int gap = 6;          //Size of gaps between cells
    
    private Row[] rows;
    
    public GameBoard() {
        
        //Create 6 rows (rows automatically fill with blank cells)
        rows = new Row[6];
        for (int i = 0; i < 6; i++) { rows[i] = new Row(i); }
        
    }

    public void clear() {
        
        //Iterate through rows in board and clear all cells in each row
        for (Row r : rows) {
            Cell[] cells = r.getCells();
            for (Cell c : cells) { c.clear(); }
        }
    }

    public Row[] getRows() { return rows; }
    
}

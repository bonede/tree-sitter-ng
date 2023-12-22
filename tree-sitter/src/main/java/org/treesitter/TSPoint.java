package org.treesitter;

public class TSPoint {
    private int row;
    private int column;

    public TSPoint(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

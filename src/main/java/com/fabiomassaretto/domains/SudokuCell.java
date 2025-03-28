package com.fabiomassaretto.domains;

public class SudokuCell {
    private final int index;
    private final int row;
    private final int col;
    private Integer value;
    private boolean isFixed;

    public SudokuCell(int row, int col, int index, Integer value, boolean isFixed) {
        this.row = row;
        this.col = col;
        this.index = index;
        this.value = value;
        this.isFixed = isFixed;
    }

    public SudokuCell getSudokuCell() {
        return this;
    }

    public int getIndex() {
        return index;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }
}

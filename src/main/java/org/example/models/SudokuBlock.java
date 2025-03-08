package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.utils.SudokuUtils.translateBlockRowsAndColumnsToBoardIndex;

/*
 *
 * Sudoku block is each section/block that will represent the whole sudoku board
 *
 * */
public class SudokuBlock {

    private Integer blockNumber = 0;
    private List<SudokuCell> sudokuCellList;

    public SudokuBlock() {
    }

    public SudokuBlock(int blockNumber) {
        this.blockNumber = blockNumber;

        sudokuCellList = new ArrayList<>();

        generateInitialSudokuBlock();
    }

    public void setSudokuCellToBlock(int index, Integer value, boolean isFixed) {
        SudokuCell sudokuCell = sudokuCellList.get(index);
        sudokuCell.setValue(value);
        sudokuCell.setFixed(isFixed);
        sudokuCellList.set(index, sudokuCell);
    }

    public void setSudokuCellToBlock(int index, Integer value) {
        SudokuCell sudokuCell = sudokuCellList.get(index);
        sudokuCell.setValue(value);
        sudokuCell.setFixed(false);
        sudokuCellList.set(index, sudokuCell);
    }

    private void generateInitialSudokuBlock() {
        int ROWS = 3;
        int COLS = 3;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int index = translateBlockRowsAndColumnsToBoardIndex(row, col);
                sudokuCellList.add(new SudokuCell(row, col, index, null, false));
            }
        }
    }

    public SudokuCell getSudokuCellByRowAndCol(int row, int col) {
        return sudokuCellList.stream().filter(s -> s.getSudokuCell().getRow() == row && s.getSudokuCell().getCol() == col).findFirst().orElse(null);
    }

    public SudokuCell getSudokuCellAtIndex(int index) {
        return sudokuCellList.stream().filter(s -> s.getSudokuCell().getIndex() == index).findFirst().orElse(null);
    }

    public List<SudokuCell> getSudokuCells() {
        return sudokuCellList.stream().map(SudokuCell::getSudokuCell).collect(Collectors.toList());
    }
}

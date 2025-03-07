package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.utils.SudokuUtils.translateRowColumnsToIndex;

/*
 *
 * Sudoku block is each section/block that will represent the whole sudoku board
 *
 * */
public class SudokuBlock {

    private Integer blockNumber = 0;

    //    private int randomValueToFill;
//    private SudokuValue[][] sudokuValues;
    private List<SudokuValue> sudokuValueList;

    public SudokuBlock() {
    }

    public SudokuBlock(int blockNumber) {
        this.blockNumber = blockNumber;
//        sudokuValues = new SudokuValue[ROWS][COLS];
        sudokuValueList = new ArrayList<>();

        generateInitialSudokuBlock();
    }

    public void setValueToBlock(int index, Integer value, boolean isFixed) {
//        sudokuValues[row][col] = new SudokuValue(value, isFixed);
        SudokuValue sudokuValue = sudokuValueList.get(index);
        sudokuValue.setValue(value);
        sudokuValue.setFixed(isFixed);
        sudokuValueList.set(index, sudokuValue);
    }

    public void setValueToBlock(int index, Integer value) {
//        sudokuValues[row][col] = new SudokuValue(value, false);
        SudokuValue sudokuValue = sudokuValueList.get(index);
        sudokuValue.setValue(value);
        sudokuValue.setFixed(false);
        sudokuValueList.set(index, sudokuValue);
    }

    private void generateInitialSudokuBlock() {
        int ROWS = 3;
        int COLS = 3;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int index = translateRowColumnsToIndex(row, col);
                sudokuValueList.add(new SudokuValue(row, col, index, null, false));
//                setValueToBlock(row, col, null, false);
            }
        }
    }

    public int getBlockNumber() {
        return blockNumber;
    }

//    public void setValueAtPosition(int row, int col, int value) {
//        sudokuBlock[row][col].setValue(value);
//    }

    public SudokuValue getValueByRowAndCol(int row, int col) {
        return sudokuValueList.stream().filter(s -> s.getSudokuValue().getRow() == row && s.getSudokuValue().getCol() == col).findFirst().orElse(null);
//        return sudokuValues[row][col];
    }

    public SudokuValue getValueAtIndex(int index) {
        return sudokuValueList.stream().filter(s -> s.getSudokuValue().getIndex() == index).findFirst().orElse(null);
//        return sudokuValues[row][col];
    }

    public List<SudokuValue> getValues() {
        return sudokuValueList.stream().map(SudokuValue::getSudokuValue).collect(Collectors.toList());
    }
}

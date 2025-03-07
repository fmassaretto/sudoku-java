package org.example;

import org.example.models.Sudoku;
import org.example.models.SudokuBlock;

public class Main {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        SudokuBlock sudokuBlock = new SudokuBlock();
//        sudoku.draw();
//        sudoku.placeNumberInSudoku(8, 1, 0, 6);
        sudoku.placeNumberInSudoku(8, 1, 0, 4);
    }
}
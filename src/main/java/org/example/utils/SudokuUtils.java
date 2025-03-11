package org.example.utils;

import org.example.exceptions.IllegalBlockStateException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SudokuUtils {
    public static final String GAME_WARNING_DISPLAY = "Warning ----> ";
    public static final String GAME_SUCCESS_DISPLAY = "Success ----> ";

    public SudokuUtils() {
    }

    public int calculateBlocksPerLineAndColumn(int totalBlocks) throws IllegalBlockStateException {
        if (totalBlocks < 4) {
            throw new IllegalBlockStateException("Total blocks must be at least 4");
        }

        for (int i = 0; i < totalBlocks; i++) {
            if ((int) Math.pow(i, 2) == totalBlocks) {
                return i;
            }
        }
        throw new IllegalBlockStateException("For blocks total of " + totalBlocks + " cannot complete the sudoku board.");
    }

    public List<String> readSudokuTemplateFile() {
        try {
            return Files.readAllLines(Paths.get("Sudoku/src/main/resources/sudoku_templates/sudoku_template_3x3_1.txt"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * This method translate from block rows and columns position to the board index of each cell.
     * <p>
     * E.g.: If sudoku board is 4x4 means that the board has 4 block in the first row and 4 blocks
     * in the first columns and 16 blocks in total, each block has 3 rows and 3 columns but the board
     * itself has index starting from 0 to 11 in the first row, in the second row starts from 12 to 23
     * and so on.
     * <p>
     *     Representing a block:</br>
     *         block 3</br>
     *      ||--0--1--2--||</br>
     *      ||--3--4--5--||</br>
     *      ||--6--7--8--||
     * <p>
     *     Representing part of a board:</br>
     *      ___block 0______block 1______block 2______block 3</br>
     *  ||--0--1--2--||--3--4--5--||--6--7--8---||--9--10--11--||</br>
     *  ||-12-13-14--||-15-16-17--||-18-19-20--||-21--22--23-||</br>
     *  ||------------||------------||------------||--------------||</br>
     *  ======================================</br>
     *     ___block 4______block 5______block 6______block 7</br>
     *  ||------------||------------||------------||--------------||</br>
     *                       So on...</br>
     *
     * @param row The integer representing an index the block row
     * @param column The integer representing an index the block column
     * @return The index of the board
     */
    public int translateBlockRowsAndColumnsToBoardIndex(int row, int column) {
        int NUM_COLS = 3;
        return row * NUM_COLS + column;
    }
}

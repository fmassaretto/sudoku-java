package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SudokuUtils {

    protected static int calculateBlocksPerLineAndColumn(int totalBlocks) {
        if (totalBlocks < 4) {
            throw new IllegalArgumentException("Total blocks must be at least 4");
        }

        for (int i = 0; i < totalBlocks; i++) {
            if ((int) Math.pow(i, 2) == totalBlocks) {
                return i;
            }
        }
        throw new IllegalArgumentException("For blocks total of " + totalBlocks + " cannot complete the sudoku board.");
    }

    protected static List<String> readSudokuTemplateFile() {
        try {
            return Files.readAllLines(Paths.get("Sudoku/src/main/resources/sudoku_templates/sudoku_template_3x3_1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
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
     *     Representing a block:
     *         block 3
     *      ||  0  1  2  ||
     *      ||  3  4  5  ||
     *      ||  6  7  8  ||
     *
     *     Representing part of a board:
     *      block 0      block 1      block 2      block 3
     *  ||  0  1  2  ||  3  4  5  ||  6  7  8  ||  9  10  11  ||
     *  || 12 13 14  ||  15 16 17 || 18 19 20  || 21  22  23  ||
     *  ||           ||           ||           ||             ||
     *  ========================================================
     *     block 4      block 5      block 6      block 7
     *  ||           ||           ||           ||             ||
     *                       So on...
     *
     * @param row The integer representing an index the block row
     * @param column The integer representing an index the block column
     * @return The index of the board
     */
    public static int translateBlockRowsAndColumnsToBoardIndex(int row, int column) {
        int NUM_COLS = 3;
        return row * NUM_COLS + column;
    }
}

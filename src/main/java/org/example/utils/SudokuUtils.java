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

    public static int translateRowColumnsToIndex(int row, int column) {
        int NUM_COLS = 3;
        return row * NUM_COLS + column;
    }
}

package org.example.models;

import org.example.utils.SudokuUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class Sudoku extends SudokuUtils {
    private static final short SUDOKU_SIZE = 9; // Size of sudoku board, eg. 9x9
    private static final int SUDOKU_BLOCKS_COUNT = calculateBlocksPerLineAndColumn(SUDOKU_SIZE);
    private List<SudokuBlock> sudokuBlocks;
//    private  sudokuBlocks2;

    public Sudoku() {
        this.sudokuBlocks = createSudokuBlocks();

        fillSudokuWithInitialValuesFromTemplate();
    }

    private void fillSudokuWithInitialValuesFromTemplate() {
        requireNonNull(readSudokuTemplateFile()).forEach(s -> {
            if (!s.startsWith("#")) {
                String[] lineSplit = s.split(",");
                int blockNumber = Integer.parseInt(lineSplit[0]);
                int row = Integer.parseInt(lineSplit[1]);
                int col = Integer.parseInt(lineSplit[2]);
                int value = Integer.parseInt(lineSplit[3]);

                int index = translateRowColumnsToIndex(row, col);

                sudokuBlocks.get(blockNumber).setValueToBlock(index, value, true);
            }
        });
    }

    private List<SudokuBlock> createSudokuBlocks() {
        sudokuBlocks = new ArrayList<>();
        for (int blockNumber = 0; blockNumber < SUDOKU_SIZE; blockNumber++) {
            sudokuBlocks.add(new SudokuBlock(blockNumber));
        }
        return sudokuBlocks;
    }

    public void placeNumberInSudoku(Integer blockNumber, int row, int col, int value) {
        validateNumber(value);
        checkForFixedValue(blockNumber, row, col);
        checkPositionHasAlreadyValue(blockNumber, row, col);
        boolean isValidMove = isValidMove(blockNumber, row, col, value);

        if (!isValidMove) {
            throw new IllegalArgumentException("Invalid move");
        }

        int index = translateRowColumnsToIndex(row, col);

        sudokuBlocks.get(blockNumber).setValueToBlock(index, value);

        draw();
    }

    private boolean isValidMove(Integer blockNumber, int row, int col, int value) {
        if (isNull(blockNumber)) {
            return checkWithPositionsOnly(row, col, value);
        } else {
            return checkWithBlockNumber(blockNumber, row, col, value);
        }
    }

    private boolean checkWithPositionsOnly(int row, int col, int value) {
        return false; // TODO: do all logic here
    }

    private boolean checkWithBlockNumber(Integer blockNumber, int row, int col, int value) {
        // check inside the block if the number that the user passed already exists in it
        SudokuBlock sudokuBlock = sudokuBlocks.get(blockNumber);
        boolean hasValueInBlock = sudokuBlock.getValues().stream().noneMatch(s -> !isNull(s.getSudokuValue().getValue()) && s.getSudokuValue().getValue().equals(value));
        // check the row in all blocks that the row traverse if the number that the user passed already exists
        checkIfValueIsUniqueInRow(blockNumber, row, value);
        // check the column in all blocks that the column traverse if the number that the user passed already exists

        return hasValueInBlock;
    }

    private void checkIfValueIsUniqueInRow(int blockNumber, int row, int value) {
        List<SudokuBlock> neighbourBlocks = getNeighbourBlocksRow(blockNumber);
        int blockColSize = 3;

        for (int col = 0; col < blockColSize; col++) {
            int finalCol = col;
            neighbourBlocks.forEach(v -> {
                Integer valueInsideBlock = v.getValueByRowAndCol(row, finalCol).getValue();
                if(nonNull(valueInsideBlock)) {
                    if (valueInsideBlock == value) {
                        throw new IllegalArgumentException("The value %d is already in the row".formatted(value));
                    }
                }
            });
        }
    }

    private List<SudokuBlock> getNeighbourBlocksRow(int blockNumber) {
        int count = 0;
        List<SudokuBlock> neighbourBlocks = new ArrayList<>();
        while (count < SUDOKU_SIZE){
            if(blockNumber <= (count + SUDOKU_BLOCKS_COUNT)) {
                for (int i = count; i < (count + SUDOKU_BLOCKS_COUNT); i++) {
                    neighbourBlocks.add(sudokuBlocks.get(i));
                }
                break;
            }
            count += SUDOKU_BLOCKS_COUNT;
        }
        return neighbourBlocks;
    }

    public void getNeighbourBlocksColumn(int row, int col) {

    }

    private void validateNumber(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("Number must be between 1 and 9");
        }
    }

    private void checkForFixedValue(int blockNumber, int row, int col) {
        int index = translateRowColumnsToIndex(row, col);
        SudokuValue getSudokuValue = sudokuBlocks.get(blockNumber).getValueAtIndex(index);

        if (getSudokuValue.isFixed()) {
            throw new IllegalArgumentException("This position has a fixed value");
        }
    }

    private void checkPositionHasAlreadyValue(int blockNumber, int row, int col) {
        int index = translateRowColumnsToIndex(row, col);
        SudokuValue getSudokuValue = sudokuBlocks.get(blockNumber).getValueAtIndex(index);

        if (getSudokuValue.getValue() != null) {
            throw new IllegalArgumentException("This position has already a value");
        }
    }


    public void draw() {
        System.out.print("    =======");
        System.out.println("===================".repeat(SUDOKU_BLOCKS_COUNT));
        // Because the way that terminal draw the data, the workaround was:
        for (int blockLine = 0; blockLine < SUDOKU_BLOCKS_COUNT; blockLine++) { // represent this is as the entire row of blocks, in a 9x9 we have 3 rows of block
            for (int row = 0; row < 3; row++) { // for each block this goes in every row of the block
                System.out.print("  ||    ");
                for (int blockIdx = 0; blockIdx < SUDOKU_BLOCKS_COUNT; blockIdx++) { // this keep track of SudokuBlock object in the list
                    for (int col = 0; col < 3; col++) { // for each block this goes in every column of the block
                        // get sudokuBlock by index of the SUDOKU_BLOCKS_COUNT (eg.: 3) and draw the row
                        int index = translateRowColumnsToIndex(row, col);
                        Integer value = sudokuBlocks.get(blockLine * SUDOKU_BLOCKS_COUNT + blockIdx).getValueAtIndex(index).getValue();
                        System.out.print(!isNull(value) ? value + "    " : "     ");
                    }
                    System.out.print("||    ");
                }
                System.out.println();
            }
            System.out.print("    =======");
            System.out.println("===================".repeat(SUDOKU_BLOCKS_COUNT));
        }
//            System.out.println("==%d==%d==%d==".formatted((counter), (counter + 1), (counter + 2)).repeat(SUDOKU_BLOCKS_COUNT));
//            counter += 3;
    }
//private static final short SUDOKU_SIZE = 9; // Standard 9x9 Sudoku
//    private static final int BLOCK_SIZE = 3; // Each sub-grid is 3x3
//    private final List<SudokuBlock> sudokuBlocks;
//
//    public Sudoku() {
//        this.sudokuBlocks = new ArrayList<>();
//        initializeSudokuGrid();
//    }
//
//    private void initializeSudokuGrid() {
//        for (int i = 0; i < SUDOKU_SIZE; i++) {
//            sudokuBlocks.add(new SudokuBlock());
//        }
//    }

//    public void draw() {
//        for (int bigRow = 0; bigRow < BLOCK_SIZE; bigRow++) {
//            for (int row = 0; row < BLOCK_SIZE; row++) {
//                for (int bigCol = 0; bigCol < BLOCK_SIZE; bigCol++) {
//                    for (int col = 0; col < BLOCK_SIZE; col++) {
//                        System.out.print(sudokuBlocks.get(bigRow * BLOCK_SIZE + bigCol)
//                                .getValueAtPosition(row, col) + " ");
//                    }
//                    System.out.print(" | "); // Separate blocks
//                }
//                System.out.println();
//            }
//            System.out.println("----------------------"); // Separate 3x3 groups
//        }
//    }
}

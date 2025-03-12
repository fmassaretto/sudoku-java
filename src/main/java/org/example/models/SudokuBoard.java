package org.example.models;

import org.example.exceptions.DuplicateValueException;
import org.example.exceptions.FixCellValueException;
import org.example.exceptions.IllegalBlockStateException;
import org.example.exceptions.InvalidMoveException;
import org.example.exceptions.UserInputNumberOutOfRangeException;
import org.example.utils.SudokuUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class SudokuBoard extends SudokuUtils {
    private final SudokuUtils sudokuUtils;
    private List<SudokuBlock> sudokuBlocks;

    private final short SUDOKU_BOARD_SIZE; // Size of sudoku board, e.g.: 3x3 = 9, 4x4 = 16 and so on
    private final int SUDOKU_BLOCKS_COUNT;

    public SudokuBoard(short sudokuBoardSize, SudokuUtils sudokuUtils) throws IllegalBlockStateException {
        SUDOKU_BOARD_SIZE = sudokuBoardSize;
        SUDOKU_BLOCKS_COUNT = this.calculateBlocksPerLineAndColumn(this.SUDOKU_BOARD_SIZE);
        this.sudokuUtils = sudokuUtils;
        this.sudokuBlocks = createSudokuBlocks();

        fillSudokuBlockWithInitialSudokuCellFromTemplate();
    }

    private void fillSudokuBlockWithInitialSudokuCellFromTemplate() {
        requireNonNull(readSudokuTemplateFile()).forEach(s -> {
            if (!s.startsWith("#")) {
                String[] lineSplit = s.split(",");
                int blockNumber = Integer.parseInt(lineSplit[0]);
                int row = Integer.parseInt(lineSplit[1]);
                int col = Integer.parseInt(lineSplit[2]);
                int value = Integer.parseInt(lineSplit[3]);

                int index = sudokuUtils.translateBlockRowsAndColumnsToBoardIndex(row, col);

                sudokuBlocks.get(blockNumber).setSudokuCellToBlock(index, value, true);
            }
        });
    }

    private List<SudokuBlock> createSudokuBlocks() {
        sudokuBlocks = new ArrayList<>();
        for (int blockNumber = 0; blockNumber < SUDOKU_BOARD_SIZE; blockNumber++) {
            sudokuBlocks.add(new SudokuBlock(sudokuUtils));
        }
        return sudokuBlocks;
    }

    public void placeNumber(Integer blockNumber, int row, int col, int value) throws UserInputNumberOutOfRangeException, InvalidMoveException, DuplicateValueException {
        validateNumber(value);
        checkForFixedValue(blockNumber, row, col);
        checkPositionHasAlreadyValue(blockNumber, row, col);
        isValidMove(blockNumber, row, col, value);

        int index = sudokuUtils.translateBlockRowsAndColumnsToBoardIndex(row, col);

        sudokuBlocks.get(blockNumber).setSudokuCellToBlock(index, value);

        System.out.println();
        System.out.printf(GAME_SUCCESS_DISPLAY + "The number %d was added!", value);
        System.out.println();
    }

    public void eraseNumber(Integer blockNumber, int row, int col) throws FixCellValueException, InvalidMoveException {
        int index = sudokuUtils.translateBlockRowsAndColumnsToBoardIndex(row, col);

        if (sudokuBlocks.get(blockNumber).getSudokuCellAtIndex(index).isFixed()) {
            throw new FixCellValueException(GAME_WARNING_DISPLAY + "The value is a fixed value that cannot be removed.");
        }

        SudokuBlock sudokuBlock = sudokuBlocks.get(blockNumber);

        Integer numberBefore = sudokuBlock.getSudokuCellAtIndex(index).getValue();

        Optional<SudokuCell> sudokuCell = sudokuBlock.deleteSudokuCell(index);

        if (sudokuCell.isEmpty()) {
            throw new InvalidMoveException(GAME_WARNING_DISPLAY + "There is no value in this position to be removed! Choose a valid one.");
        }

        System.out.println();
        System.out.printf(GAME_SUCCESS_DISPLAY + "The number %d was removed!", numberBefore);
        System.out.println();
    }

    private void isValidMove(Integer blockNumber, int row, int col, int value) throws DuplicateValueException, InvalidMoveException {
        if (isNull(blockNumber)) {
            checkWithPositionsOnly(row, col, value);
        } else {
            checkWithBlockNumber(blockNumber, row, col, value);
        }
    }

    private void checkWithPositionsOnly(int row, int col, int value) {
        // TODO: do all logic here to check the value only with row
        //  and column of the entire board and get rid of the need to inform the block number
    }

    private void checkWithBlockNumber(Integer blockNumber, int row, int col, int value) throws DuplicateValueException, InvalidMoveException {
        // check inside the block if the number that the user passed already exists in it
        SudokuBlock sudokuBlock = sudokuBlocks.get(blockNumber);

        boolean hasValueInBlock = sudokuBlock
                .getSudokuCells()
                .stream()
                .anyMatch(s -> !isNull(s.getSudokuCell().getValue()) && s.getSudokuCell().getValue().equals(value));

        if (hasValueInBlock) {
            throw new InvalidMoveException(GAME_WARNING_DISPLAY + "This block has already the same number!");
        }

        // check the row in all blocks that the row traverse if the number that the user passed already exists
        checkForUniqueValueInRowOrColumn(blockNumber, Directions.ROW, row, value);
        // check the column in all blocks that the column traverse if the number that the user passed already exists
        checkForUniqueValueInRowOrColumn(blockNumber, Directions.COLUMN, col, value);
    }

    // For the value inside a block that the user is placing this method gets all neighbour blocks
    // to later check all values in the row for each block
    private List<SudokuBlock> getNeighbourBlocksRow(int blockNumber) {
        int count = 0;
        List<SudokuBlock> neighbourBlocks = new ArrayList<>();
        while (count < SUDOKU_BOARD_SIZE) {
            if (blockNumber < (count + SUDOKU_BLOCKS_COUNT)) {
                for (int i = count; i < (count + SUDOKU_BLOCKS_COUNT); i++) {
                    neighbourBlocks.add(sudokuBlocks.get(i));
                }
                break;
            }
            count += SUDOKU_BLOCKS_COUNT;
        }
        return neighbourBlocks;
    }

    private void checkForUniqueValueInRowOrColumn(Integer blockNumber, Directions direction, int rowOrCol, int value) throws DuplicateValueException {
        int blockRowColSize = 3;
        boolean isDirectionRow = direction.equals(Directions.ROW);

        List<SudokuBlock> neighbourBlocks = isDirectionRow ? getNeighbourBlocksRow(blockNumber) : getNeighbourBlocksColumn(blockNumber);

        for (int i = 0; i < blockRowColSize; i++) {
            for (SudokuBlock v : neighbourBlocks) {
                Integer valueInsideBlock = isDirectionRow ?
                        v.getSudokuCellByRowAndCol(rowOrCol, i).getValue() :
                        v.getSudokuCellByRowAndCol(i, rowOrCol).getValue();

                if (nonNull(valueInsideBlock) && valueInsideBlock == value) {
                    throw new DuplicateValueException(GAME_WARNING_DISPLAY + "The value %d is already in the %s"
                            .formatted(value, isDirectionRow ? "row" : "column"));
                }
            }
        }
    }

    public List<SudokuBlock> getNeighbourBlocksColumn(int blockNumber) {
        List<SudokuBlock> neighbourBlocks = List.of();
        int index;
        for (int i = 0; i < SUDOKU_BLOCKS_COUNT; i++) {
            index = i;
            neighbourBlocks = new ArrayList<>();
            for (int j = 0; j < SUDOKU_BLOCKS_COUNT; j++) {
                neighbourBlocks.add(sudokuBlocks.get(index));
                index += SUDOKU_BLOCKS_COUNT;
            }

            if (neighbourBlocks.contains(sudokuBlocks.get(blockNumber))) {
                break;
            }
        }
        return neighbourBlocks;
    }

    private void validateNumber(int value) throws UserInputNumberOutOfRangeException {
        if (value < 1 || value > 9) {
            throw new UserInputNumberOutOfRangeException(GAME_WARNING_DISPLAY + "Number must be between 1 and 9");
        }
    }

    private void checkForFixedValue(int blockNumber, int row, int col) throws InvalidMoveException {
        int index = sudokuUtils.translateBlockRowsAndColumnsToBoardIndex(row, col);
        SudokuCell getSudokuCell = sudokuBlocks.get(blockNumber).getSudokuCellAtIndex(index);

        if (getSudokuCell.isFixed()) {
            throw new InvalidMoveException(GAME_WARNING_DISPLAY + "This cell has a fixed value that cannot be changed!");
        }
    }

    private void checkPositionHasAlreadyValue(int blockNumber, int row, int col) throws InvalidMoveException {
        int index = sudokuUtils.translateBlockRowsAndColumnsToBoardIndex(row, col);
        SudokuCell getSudokuCell = sudokuBlocks.get(blockNumber).getSudokuCellAtIndex(index);

        if (getSudokuCell.getValue() != null) {
            throw new InvalidMoveException(GAME_WARNING_DISPLAY + "This position has already a value");
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
                        int index = sudokuUtils.translateBlockRowsAndColumnsToBoardIndex(row, col);
                        Integer value = sudokuBlocks.get(blockLine * SUDOKU_BLOCKS_COUNT + blockIdx).getSudokuCellAtIndex(index).getValue();
                        System.out.print(!isNull(value) ? value + "    " : "     ");
                    }
                    System.out.print("||    ");
                }
                System.out.println();
            }
            System.out.print("    =======");
            System.out.println("===================".repeat(SUDOKU_BLOCKS_COUNT));
        }
    }
}

package org.example.utils;

import org.example.models.Sudoku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SudokuUtilsTest {

    @InjectMocks
    SudokuUtils sudokuUtils;

    @Mock
    Files files;

    @BeforeEach
    void setUp() throws IOException {
        String fileName = "src/test/resources/sudoku_template_3x3_test_1.txt";

        Files.readAllLines(Paths.get(fileName));
    }

    @Test
    void whenPassing9AsParameterShouldReturn3() {
        int result = SudokuUtils.calculateBlocksPerLineAndColumn( 9);
        assertEquals(3, result);
    }

    @Test
    void whenPassing12AsParameterShouldReturn4() {
        int result = SudokuUtils.calculateBlocksPerLineAndColumn( 9);
        assertEquals(3, result);
    }

    @Test
    void whenPassingValueLessThan4AsParameterShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> SudokuUtils.calculateBlocksPerLineAndColumn( 3));
    }

    @Test
    void whenPassingValueThatDontGenerateAPerfectSquareAsParameterShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> SudokuUtils.calculateBlocksPerLineAndColumn( 15));
    }
}
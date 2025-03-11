package org.example.utils;

import org.example.exceptions.IllegalBlockStateException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class SudokuUtilsTest {

    @InjectMocks
    SudokuUtils sudokuUtils;

//    @Mock
//    Files files;

    String fileName = "src/test/resources/sudoku_template_3x3_test_1.txt";

//    @BeforeAll
//    void initMocks() {
////        MockitoAnnotations.openMocks(this);
//    }
    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
//        files.readAllLines(paths.get(fileName));
    }

    @Test
    void whenPassing9AsParameterShouldReturn3() throws IllegalBlockStateException {
        int result = sudokuUtils.calculateBlocksPerLineAndColumn( 9);
        assertEquals(3, result);
    }

    @Test
    void whenPassing12AsParameterShouldReturn4() throws IllegalBlockStateException {
        int result = sudokuUtils.calculateBlocksPerLineAndColumn( 9);
        assertEquals(3, result);
    }

    @Test
    void whenPassingValueLessThan4AsParameterShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sudokuUtils.calculateBlocksPerLineAndColumn( 3));
    }

    @Test
    void whenPassingValueThatDontGenerateAPerfectSquareAsParameterShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sudokuUtils.calculateBlocksPerLineAndColumn( 15));
    }

    @Test
    void calculateBlocksPerLineAndColumn() {
    }

    @Test
    void readSudokuTemplateFile() throws IOException {
        List<String> lines = List.of("# blockNumber, row, col, value",
                "0,0,0,5",
                "0,0,1,3",
                "0,1,0,6",
                "0,2,1,9",
                "0,2,2,8",
                "1,0,1,7",
                "1,1,0,1",
                "1,1,1,9",
                "1,1,2,5",
                "2,2,1,6",
                "3,0,0,8",
                "3,1,0,4",
                "3,2,0,7",
                "4,0,1,6",
                "4,1,0,8",
                "4,1,2,3",
                "4,2,1,2");

        Path paths1 = Paths.get(fileName);

        when(Files.readAllLines(paths1)).thenReturn(lines);

        List<String> result = sudokuUtils.readSudokuTemplateFile();

        assertEquals(lines, result);
    }

    @Test
    void readSudokuTemplateFileThrowIOException() throws IOException {
        Path paths1 = Paths.get("");

        mockStatic(Files.class);

//        doThrow(IOException.class).when(Files.readAllLines(paths1));

        when(Files.readAllLines(paths1)).thenThrow(IOException.class);

        assertThrows(IOException.class, sudokuUtils::readSudokuTemplateFile);
    }

    @ParameterizedTest
    @MethodSource("generateData")
    void translateBlockRowsAndColumnsToBoardIndex(int row , int col, int expectedResult) {

        int result = sudokuUtils.translateBlockRowsAndColumnsToBoardIndex(row, col);

        assertEquals(expectedResult, result);
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(0, 0, 0),
                Arguments.of(0, 1, 1),
                Arguments.of(0, 2, 2),
                Arguments.of(1, 0, 3),
                Arguments.of(1, 1, 4),
                Arguments.of(1, 2, 5),
                Arguments.of(2, 0, 6),
                Arguments.of(2, 1, 7),
                Arguments.of(2, 2, 8)
        );
    }
}
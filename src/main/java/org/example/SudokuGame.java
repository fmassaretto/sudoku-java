package org.example;

import org.example.exceptions.DuplicateValueException;
import org.example.exceptions.FixCellValueException;
import org.example.exceptions.IllegalBlockStateException;
import org.example.exceptions.InvalidMoveException;
import org.example.exceptions.UserInputNumberOutOfRangeException;
import org.example.models.SudokuBoard;
import org.example.utils.SudokuUtils;

import java.util.Scanner;

public class SudokuGame {
    private final Scanner sc = new Scanner(System.in);
    private SudokuBoard sudokuBoard;
    short sudokuBoardSize = 9;

    public SudokuGame() throws IllegalBlockStateException {
        sudokuBoard = new SudokuBoard(sudokuBoardSize, new SudokuUtils());
    }

    public void start() {
        System.out.println();
        sudokuBoard.draw();
        gameMenu();
    }

    private void gameMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println();
            System.out.println("********* SUDOKU JAVA ********");
            System.out.println("(1) Preencher um numero");
            System.out.println("(2) Remover um numero");
            System.out.println("(3) Ver o jogo");
            System.out.println("(0) Sair do jogo");
            System.out.print("Digite o número da opção: ");
            int option = sc.nextInt();
            System.out.println("******************************");
            System.out.println();

            switch (option) {
                case 1:
                    fillNumber();
                    System.out.println();
                    break;
                case 2:
                    removeNumber();
                    System.out.println();
                    break;
                case 3:
                    showGame();
                    System.out.println();
                    break;
                case 0:
                    exit = true;
                    System.out.println("\n\n");
                    break;
                default:
                    System.out.println("Invalid Option! Choose a valid option.");
                    break;
            }
        }
    }

    private void showGame() {
        System.out.println("showGame()");
        sudokuBoard.draw();
    }

    private void removeNumber() {
        System.out.println("removeNumber()");
        try {
            System.out.print("Digite o número do bloco : ");
            int blockNumber = sc.nextInt();
            System.out.print("Digite o número da linha: ");
            int row = sc.nextInt();
            System.out.print("Digite o número da coluna: ");
            int col = sc.nextInt();

            sudokuBoard.eraseNumber(blockNumber, row, col);
        } catch (FixCellValueException | InvalidMoveException e) {
            System.out.println();
            System.out.println(e.getMessage());
            System.out.println();
        }
        sudokuBoard.draw();
    }

    private void fillNumber() {
        System.out.println("fillNumber()");
        try {
            System.out.print("Digite o número do bloco : ");
            int blockNumber = sc.nextInt();
            System.out.print("Digite o número da linha: ");
            int row = sc.nextInt();
            System.out.print("Digite o número da coluna: ");
            int col = sc.nextInt();
            System.out.print("Digite o numero de 1 a 9 para preencher o sudoku: ");
            int value = sc.nextInt();

            sudokuBoard.placeNumber(blockNumber, row, col, value);
        } catch (UserInputNumberOutOfRangeException | InvalidMoveException | DuplicateValueException e) {
            System.out.println();
            System.out.println(e.getMessage());
            System.out.println();
        }
        sudokuBoard.draw();
    }
}

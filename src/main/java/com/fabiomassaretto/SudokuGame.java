package com.fabiomassaretto;

import com.fabiomassaretto.domains.GameStatus;
import com.fabiomassaretto.exceptions.DuplicateValueException;
import com.fabiomassaretto.exceptions.FixCellValueException;
import com.fabiomassaretto.exceptions.IllegalBlockStateException;
import com.fabiomassaretto.exceptions.InvalidMoveException;
import com.fabiomassaretto.exceptions.UserInputNumberOutOfRangeException;
import com.fabiomassaretto.domains.SudokuBoard;
import com.fabiomassaretto.utils.SudokuUtils;

import java.util.Scanner;

public class SudokuGame {
    private final Scanner sc = new Scanner(System.in);
    private final SudokuBoard sudokuBoard;
    private GameStatus gameStatus;
    private boolean exit = false;


    public SudokuGame() throws IllegalBlockStateException {
        short sudokuBoardSize = 9;
        sudokuBoard = new SudokuBoard(sudokuBoardSize, new SudokuUtils());

        this.gameStatus = GameStatus.STARTED;
    }

    public void start() {
        System.out.println();
        sudokuBoard.draw();
        gameMenu();
    }

    private void gameMenu() {
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
        sudokuBoard.draw();
    }

    private void removeNumber() {
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

            checkGameWon();

            if (gameStatus == GameStatus.WON) {
                gameWon();
                return;
            }
        } catch (UserInputNumberOutOfRangeException | InvalidMoveException | DuplicateValueException e) {
            System.out.println();
            System.out.println(e.getMessage());
            System.out.println();
        }
        sudokuBoard.draw();
    }

    private void checkGameWon() {
        gameStatus = sudokuBoard.gameWonStatus();
    }

    private void gameWon() {
        exit = true;
        System.out.println("\n\n");
        wonMessageDisplay();
        sudokuBoard.draw();
        wonMessageDisplay();
        System.out.println("\n\n");
    }

    private void wonMessageDisplay(){
        System.out.println("                    ***************************");
        System.out.println("                       Parabéns! Você venceu!  ");
        System.out.println("                    ***************************");
        System.out.println();
    }
}

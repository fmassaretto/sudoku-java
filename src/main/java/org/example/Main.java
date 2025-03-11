package org.example;

import org.example.exceptions.DuplicateValueException;
import org.example.exceptions.IllegalBlockStateException;
import org.example.exceptions.InvalidMoveException;
import org.example.exceptions.UserInputNumberOutOfRangeException;
import org.example.models.GameStatus;
import org.example.models.SudokuBoard;
import org.example.utils.SudokuUtils;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
//        sudoku.draw();
//        sudoku.placeNumberInSudoku(8, 1, 0, 6);
        try {
            menu();
        } catch (IllegalBlockStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void menu() throws IllegalBlockStateException {
        SudokuGame sudokuGame = new SudokuGame();
        GameStatus status = GameStatus.NOT_STARTED;


        while(!status.equals(GameStatus.EXITED)) {
            System.out.println("********* SUDOKU JAVA ********");
            System.out.println("(1) Iniciar novo jogo");
            System.out.println("(0) Sair");
            System.out.println();
            System.out.print("Digite o número da opção: ");
            int option = sc.nextInt();
            System.out.println("******************************");

            switch (option) {
                case 1: sudokuGame.start(); break;
                case 0: status = GameStatus.EXITED; break;
                default:
                    System.out.println();
                    System.out.println("Opção inválida! Tente novamente!");
                    System.out.println();
                    break;
            }
        }
    }
}
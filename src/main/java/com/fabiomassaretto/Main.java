package com.fabiomassaretto;

import com.fabiomassaretto.exceptions.IllegalBlockStateException;
import com.fabiomassaretto.domains.GameStatus;
import com.fabiomassaretto.domains.SudokuAbout;
import com.fabiomassaretto.domains.SudokuHelp;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
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
            System.out.println("(2) Ajuda");
            System.out.println("(3) Sobre");
            System.out.println("(0) Sair");
            System.out.println();
            System.out.print("Digite o número da opção: ");
            int option = sc.nextInt();
            System.out.println("******************************");

            switch (option) {
                case 1: sudokuGame.start(); break;
                case 2:
                    System.out.println(SudokuHelp.show()); break;
                case 3:
                    System.out.println(SudokuAbout.show()); break;
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
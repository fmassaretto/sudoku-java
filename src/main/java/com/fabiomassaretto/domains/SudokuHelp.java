package com.fabiomassaretto.domains;

public class SudokuHelp {
    private SudokuHelp() {
    }

    public static String show() {
        return """
                 *** AJUDA ***
                \s
                 Ao escolher a opção number 1 do menu principal, o jogo inicia com o sudoku inicialmente\s
                 preenchido em algumas posições, como no exemplo abaixo de um jogo de tamanho 3x3:
                \s
                     ================================================================
                    ||    5    3         ||         7         ||                   ||   \s
                    ||    6              ||    1    9    5    ||                   ||   \s
                    ||         9    8    ||                   ||         6         ||   \s
                      ================================================================
                    ||    8              ||         6         ||              3    ||   \s
                    ||    4              ||    8         3    ||              1    ||   \s
                    ||    7              ||         2         ||              6    ||   \s
                      ================================================================
                    ||         6         ||                   ||    2    8         ||   \s
                    ||                   ||    4    1    9    ||              5    ||   \s
                    ||                   ||         8         ||         7    9    ||   \s
                      ================================================================
                \s
                O Sudoku Java funciona da seguinte maneira: no exemplo acima temos 9 quadrados, 
                chamados blocos (começando do zero), exemplo:
                 \s
                      ================================================================
                     ||                   ||                   ||                   ||   \s
                     ||      bloco #0     ||      bloco #1     ||      bloco #2     ||   \s
                     ||                   ||                   ||                   ||   \s
                       ================================================================
                     ||                   ||                   ||                   ||   \s
                     ||      bloco #3     ||      bloco #4     ||      bloco #5     ||   \s
                     ||                   ||                   ||                   ||   \s
                       ================================================================
                     ||                   ||                   ||                   ||   \s
                     ||      bloco #6     ||      bloco #7     ||      bloco #8     ||   \s
                     ||                   ||                   ||                   ||   \s
                       ================================================================
                 \s
                  \s
                E cada bloco contém 3 linhas(L) e 3 colunas (C), totalizando 9 posições para digitar um número 
                entre 1 e 9, exemplo:
                  \s
                     C     0     1     2        0     1     2       ...
                    L =============================================
                    0 ||   0     1     2   ||                   ||     \s
                    1 ||   3     4     5   ||      bloco #1     ||    Outros blocos omitidos \s
                    2 ||   6     7     8   ||                   ||     \s
                        ============================================
                    0 ||                   ||      \s
                    1 ||      bloco #3     ||      Outros blocos omitidos \s
                    2 ||                   ||      \s
                        ======================
                    . ||                   ||     \s
                    .   Outros blocos omitidos
                    .
                    
                  Então para tanto para colocar ou remover um valor na posição 5, por exemplo, 
                  do bloco #3, temos que digitar:
                  
                  1 - O número do bloco, no caso:  3
                  2 - O número da linha, no caso:  1
                  3 - O número da coluna, no caso: 2
                  
                 Ficando assim, se for colocar o número 7 nessa posição:
                   \s
                     C     0     1     2        0     1     2       ...
                    L =============================================
                    0 ||                   ||                   ||     \s
                    1 ||      bloco #0     ||      bloco #1     ||    Outros blocos omitidos \s
                    2 ||                   ||                   ||     \s
                        ============================================
                    0 ||                   ||      \s
                    1 ||               7   ||      Outros blocos omitidos \s
                    2 ||                   ||      \s
                        ======================
                    . ||                   ||     \s
                    .   Outros blocos omitidos
                    .
                  
                 *** AJUDA ***
                \s""";
    }
}

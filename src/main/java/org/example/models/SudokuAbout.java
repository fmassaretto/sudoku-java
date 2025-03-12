package org.example.models;

public class SudokuAbout {
    private SudokuAbout() {
    }

    public static String show() {
        return """
                 *** SOBRE ***
                \s
                 Jogo imitando o famoso jogo de revistinhas e jornais, Sudoku. Criado em Java para jogar no console,\s
                 e toda a lógica por trás do jogo foi desenvolvido do zero, sem consultar outro projetos para que eu\s
                 possa me desafiar e testar meus conhecimentos. Obervação importante: Tem muitos pontos do backend\s
                 que podem e serão melhorados no futuro, no primeiro momento ele não foi pensado para ser perfomático.
                \s
                \s
                 Desenvolvedor: Fábio Massaretto
                 Github: https://github.com/fmassaretto
                 Linkedin: https://www.linkedin.com/in/fmassaretto/
                \s
                 Versão do Java: 21
                \s
                 *** SOBRE ***
                \s""";
    }
}

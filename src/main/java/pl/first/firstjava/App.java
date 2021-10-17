package pl.first.firstjava;

/**
 * Klasa główna z metodą main.
 *
 * @author Marcin Kwapisz
 */
public class App {
    public static void main(final String[] args) {
        Greeter greeter = new Greeter();
        System.out.print(greeter.greet("Witalij" + args[0]));
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        sudoku.printBoard();
    }
}


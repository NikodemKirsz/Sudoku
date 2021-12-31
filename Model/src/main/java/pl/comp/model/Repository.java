package pl.comp.model;

public class Repository {
    public SudokuBoard sudokuBoard;

    public Repository(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    public SudokuBoard createInstance() throws CloneNotSupportedException {
        return sudokuBoard.clone();
    }
}

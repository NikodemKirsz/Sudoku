package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    @Test
    void read() {
        FileSudokuBoardDao fileSudokuBoardDao = new FileSudokuBoardDao("./file");
        SudokuBoard sudokuBoard = fileSudokuBoardDao.read();
        sudokuBoard.printBoard();
    }

    @Test
    void write() {
        FileSudokuBoardDao fileSudokuBoardDao = new FileSudokuBoardDao("./file");
        SudokuBoard sudokuBoard = new SudokuBoard();
        fileSudokuBoardDao.write(sudokuBoard);
    }
}
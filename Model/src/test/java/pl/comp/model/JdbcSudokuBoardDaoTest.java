package pl.comp.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {
    final static String PATH_TO_DIRECTORY = "files/";
    static File testDirectory;

    @Test
    void read() {
        var jdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao();
        jdbc.read();
        var sudokuBoard = new SudokuBoard();
        sudokuBoard.solveGame();
        jdbc.write(sudokuBoard, sudokuBoard);
        jdbc.read();
        var boards = jdbc.readBoth(2);
        boards.getValue0().printBoard();
        boards.getValue1().printBoard();
        jdbc.updateBoard(3, boards.getValue0(), boards.getValue1());
        jdbc.isRecordEmpty(2);
    }

//    /*@BeforeAll
//    static void beforeAll() {
//        testDirectory = new File(PATH_TO_DIRECTORY);
//        if (testDirectory.exists()) {
//            assertTrue(deleteDirectory(testDirectory));
//        }
//        assertTrue(testDirectory.mkdir());
//    }
//
//
//    @AfterAll
//    static void afterAll() {
//        assertTrue(deleteDirectory(testDirectory));
//    }
//
//    static boolean deleteDirectory(File dir)
//    {
//        boolean success = true;
//        File[] allContents = dir.listFiles();
//        assert allContents != null;
//        for (File file : allContents) {
//            success &= file.delete();
//        }
//        success &= dir.delete();
//        return success;
//    }*/
}
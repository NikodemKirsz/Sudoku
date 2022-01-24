package pl.comp.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {
    final static String PATH_TO_DIRECTORY = "test_files";
    final static String DB_NAME = "test_sudoku_boards.db";
    final static String DB_PATH = Paths.get(PATH_TO_DIRECTORY, DB_NAME).toString();
    static File testDirectory;

    @BeforeAll
    static void beforeAll(){
        testDirectory = new File(PATH_TO_DIRECTORY);
        if (testDirectory.exists()) {
            assertTrue(deleteDirectory(testDirectory));
        }
        assertTrue(testDirectory.mkdir());
        System.out.println("Database Path = " + DB_PATH);
    }

    @Test
    void read() {
        var jdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao(DB_PATH);
        jdbc.initialize();
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

    @AfterAll
    static void afterAll() {
        assertTrue(deleteDirectory(testDirectory));
    }

    static boolean deleteDirectory(File dir)
    {
        boolean success = true;
        File[] allContents = dir.listFiles();
        assert allContents != null;
        for (File file : allContents) {
            success &= file.delete();
        }
        success &= dir.delete();
        return success;
    }
}
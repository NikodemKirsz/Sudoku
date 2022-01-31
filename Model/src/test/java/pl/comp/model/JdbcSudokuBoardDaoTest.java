package pl.comp.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.comp.exceptions.OutOfDatabaseException;
import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static pl.comp.model.JdbcSudokuBoardDao.BoardType;

class JdbcSudokuBoardDaoTest {
    private final static String PATH_TO_DIRECTORY = "test_files";
    private final static String DB_NAME = "test_sudoku_boards.db";
    private final static String DB_PATH = Paths.get(PATH_TO_DIRECTORY, DB_NAME).toString();

    private static File testDirectory;
    private static JdbcSudokuBoardDao origJdbc;
    private static JdbcSudokuBoardDao currJdbc;

    @BeforeAll
    static void beforeAll(){
        testDirectory = new File(PATH_TO_DIRECTORY);
        if (testDirectory.exists()) {
            assertTrue(deleteDirectory(testDirectory));
        }
        assertTrue(testDirectory.mkdir());
        System.out.println("Database Path = " + DB_PATH);

        JdbcSudokuBoardDao.initialize();
        origJdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao(BoardType.ORIGINAL);
        currJdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao(BoardType.CURRENT);
    }

    @Test
    void writeAndReadTest() {
        var sudokuBoard = new SudokuBoard();
        origJdbc.write(sudokuBoard, 2);

        sudokuBoard.solveGame();
        currJdbc.write(sudokuBoard, 2);

        var readBoard = currJdbc.read(2).getBoard();
        var savedBoard = sudokuBoard.getBoard();
        for (var i = 0; i < 9; i++) {
            for (var j = 0; j < 9; j++) {
                assertEquals(readBoard[i][j].getFieldValue(),
                        savedBoard[i][j].getFieldValue());
            }
        }

        var sudokuBoard2 = new SudokuBoard();
        origJdbc.write(sudokuBoard2, 2);

        sudokuBoard2.solveGame();
        currJdbc.write(sudokuBoard2, 2);

        var readBoard2 = currJdbc.read(2).getBoard();
        var savedBoard2 = sudokuBoard2.getBoard();
        for (var i = 0; i < 9; i++) {
            for (var j = 0; j < 9; j++) {
                assertEquals(readBoard2[i][j].getFieldValue(),
                        savedBoard2[i][j].getFieldValue());
            }
        }


        boolean isAllEqual = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                isAllEqual &= readBoard2[i][j].getFieldValue()
                        == readBoard[i][j].getFieldValue();
            }
        }
        assertFalse(isAllEqual);
    }

    @Test
    void saveBoardExceptionTest() {
        assertThrows(OutOfDatabaseException.class, ()->origJdbc.write(new SudokuBoard(), 6));
        assertThrows(OutOfDatabaseException.class, ()->currJdbc.write(new SudokuBoard(), 6));
    }

    @AfterAll
    static void afterAll() {
        assertTrue(deleteDirectory(testDirectory));
        currJdbc = null;
        origJdbc = null;
    }

    static boolean deleteDirectory(File dir) {
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
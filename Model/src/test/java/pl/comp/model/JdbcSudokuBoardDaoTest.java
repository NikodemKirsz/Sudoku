package pl.comp.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.comp.exceptions.EmptyRecordException;
import pl.comp.exceptions.OutOfDatabaseException;
import pl.comp.exceptions.ProszeNieUzywacTejMetodyException;

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

//    /*@BeforeAll
//    static void beforeAll(){
//        testDirectory = new File(PATH_TO_DIRECTORY);
//        if (testDirectory.exists()) {
//            assertTrue(deleteDirectory(testDirectory));
//        }
//        assertTrue(testDirectory.mkdir());
//        System.out.println("Database Path = " + DB_PATH);
//
//        JdbcSudokuBoardDao.initialize();
//        origJdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao(BoardType.ORIGINAL);
//        currJdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao(BoardType.CURRENT);
//
//    }
//
//    @Test
//    void writeTest() {
//        var sudokuBoard = new SudokuBoard();
//        sudokuBoard.solveGame();
//        jdbc.updateBoard(2, sudokuBoard, sudokuBoard);
//        var readBoard = jdbc.readBoth(2).getValue0();
//        for (var i = 0; i < 9; i++) {
//            for (var j = 0; j < 9; j++) {
//                assertEquals(readBoard.getBoard()[i][j].getFieldValue(),
//                        sudokuBoard.getBoard()[i][j].getFieldValue());
//            }
//        }
//
//        var sudokuBoard2 = new SudokuBoard();
//        sudokuBoard2.solveGame();
//        jdbc.updateBoard(2, sudokuBoard2, sudokuBoard2);
//        var readBoard2 = jdbc.readBoth(2).getValue0();
//        for (var i = 0; i < 9; i++) {
//            for (var j = 0; j < 9; j++) {
//                assertEquals(readBoard2.getBoard()[i][j].getFieldValue(),
//                        sudokuBoard2.getBoard()[i][j].getFieldValue());
//            }
//        }
//
//
//        boolean isAllEqual = true;
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                isAllEqual &= readBoard2.getBoard()[i][j].getFieldValue()
//                        == sudokuBoard.getBoard()[i][j].getFieldValue();
//            }
//        }
//        assertFalse(isAllEqual);
//    }
//
//    @Test
//    void readTest() {
//        var boards = jdbc.readBoth(2);
//        assertNotNull(boards.getValue0());
//        assertNotNull(boards.getValue1());
//        assertNull(jdbc.read());
//        assertTrue(jdbc.isRecordEmpty(4));
//        assertFalse(jdbc.isRecordEmpty(2));
//    }
//
//    @Test
//    void updateBoardExceptionTest() {
//        assertThrows(OutOfDatabaseException.class, ()->jdbc.updateBoard(6,new SudokuBoard(), new SudokuBoard()));
//    }
//
//    @Test
//    void proszeNieUzywacTejMetodyExceptionTest() {
//        assertThrows(ProszeNieUzywacTejMetodyException.class, ()->jdbc.write(new SudokuBoard()));
//    }
//
//    @Test
//    void readBothException() {
//        assertThrows(EmptyRecordException.class, ()->jdbc.readBoth(1));
//    }
//
//    @AfterAll
//    static void afterAll() {
//        assertTrue(deleteDirectory(testDirectory));
//        jdbc = null;
//    }
//
//    static boolean deleteDirectory(File dir) {
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
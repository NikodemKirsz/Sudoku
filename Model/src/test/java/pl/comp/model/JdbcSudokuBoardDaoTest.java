package pl.comp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {

    @Test
    void read() {
        var jdbc = SudokuBoardDaoFactory.getDatabaseDao();
        jdbc.read();
    }
}
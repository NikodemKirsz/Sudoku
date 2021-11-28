/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.first.firstjava;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    final static String PATH_TO_SUCCESSFUL_FILE = "./files/fileSuccessful";
    final static String PATH_TO_UNSUCCESSFUL_FILE = "./files/fileUnsuccessful";
    static FileOutputStream fis;

    @BeforeAll
    static void BeforeAll() throws IOException {
        var unsuccessfulFile = new File(PATH_TO_UNSUCCESSFUL_FILE);
        if (unsuccessfulFile.isFile()) {
            boolean success = unsuccessfulFile.delete();
            if (success) {
                success = unsuccessfulFile.createNewFile();
            }
            assertTrue(success);
        }
        //assertTrue(unsuccessfulFile.setReadable(false));
        assertTrue(unsuccessfulFile.setWritable(false));
    }

    @Test
    void writeSuccessful() {
        var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_SUCCESSFUL_FILE);

        SudokuBoard sudokuBoard = new SudokuBoard();
        fileSudokuBoardDao.write(sudokuBoard);

        var f = new File(PATH_TO_SUCCESSFUL_FILE);
        assertTrue(f.exists());
        assertTrue(f.isFile());
    }

    @Test
    void writeUnsuccessful() {
        var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_UNSUCCESSFUL_FILE);

        SudokuBoard sudokuBoard = new SudokuBoard();
        fileSudokuBoardDao.write(null);
    }

    @Test
    void readSuccessful() {
        var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_SUCCESSFUL_FILE);

        var readSudokuBoard = fileSudokuBoardDao.read();
        assertNotNull(readSudokuBoard);
    }

    @Test
    void readUnsuccessful() {
        var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_UNSUCCESSFUL_FILE);

        var readSudokuBoard = fileSudokuBoardDao.read();
        assertNull(readSudokuBoard);
    }
}
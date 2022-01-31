/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.comp.exceptions.FailedFileOperationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    final static String PATH_TO_DIRECTORY = "testFiles";
    final static String PATH_TO_SUCCESSFUL_FILE = Paths.get(PATH_TO_DIRECTORY, "fileSuccessful").toString();
    final static String PATH_TO_UNSUCCESSFUL_FILE = Paths.get(PATH_TO_DIRECTORY, "fileUnsuccessful").toString();
    static File testDirectory;

    @BeforeAll
    static void beforeAll() throws IOException {
        testDirectory = new File(PATH_TO_DIRECTORY);
        if (testDirectory.exists()) {
            assertTrue(deleteDirectory(testDirectory));
        }
        assertTrue(testDirectory.mkdir());

        var unsuccessfulFile = new File(PATH_TO_UNSUCCESSFUL_FILE);

        if (unsuccessfulFile.isFile()) {
            boolean success = unsuccessfulFile.delete();
            if (success) {
                success = unsuccessfulFile.createNewFile();
            }
            assertTrue(success);
        } else {
            boolean success = unsuccessfulFile.createNewFile();
            assertTrue(success);
        }
        assertTrue(unsuccessfulFile.setWritable(false));
    }

    @Test
    void writeSuccessful() {
        int index = 0;
        try(var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_SUCCESSFUL_FILE)) {
            SudokuBoard sudokuBoard = new SudokuBoard();
            fileSudokuBoardDao.write(sudokuBoard, index);
        } catch (Exception e) {
            e.printStackTrace();
        }

        var f = new File(PATH_TO_SUCCESSFUL_FILE + index);
        assertTrue(f.exists());
        assertTrue(f.isFile());
    }

    @Test
    void writeUnsuccessful() {
        try(var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_UNSUCCESSFUL_FILE)) {
            fileSudokuBoardDao.write(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void readSuccessful() {
        int index = 0;
        SudokuBoard readSudokuBoard = null;
        try(var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_SUCCESSFUL_FILE)) {
            readSudokuBoard = fileSudokuBoardDao.read(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(readSudokuBoard);
    }

    @Test
    void readUnsuccessful() {
        try(var fileSudokuBoardDao = new FileSudokuBoardDao(PATH_TO_UNSUCCESSFUL_FILE)) {
            fileSudokuBoardDao.read(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
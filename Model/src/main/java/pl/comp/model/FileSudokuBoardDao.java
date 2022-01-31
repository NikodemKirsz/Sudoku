/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.DaoException;
import pl.comp.exceptions.FailedFileOperationException;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final String filePath;
    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);
    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("bundle");


    public FileSudokuBoardDao(String filePath) {
        this.filePath = filePath;
    }

    public SudokuBoard read(int index) {
        SudokuBoard sudokuBoard = null;
        String currentPath = filePath + String.valueOf(index);

        try (var fileInputStream = new FileInputStream(currentPath);
             var objectInputStream = new ObjectInputStream(fileInputStream)) {
            sudokuBoard = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            DaoException exception = new FailedFileOperationException(
                    resourceBundle.getString("FailedFileOperation"), e);
            logger.error(exception + resourceBundle.getString("cause"), exception.getCause());
        }
        return sudokuBoard;
    }

    public void write(SudokuBoard obj, int index) {
        String currentPath = filePath + String.valueOf(index);

        try (var fileOutputStream = new FileOutputStream(currentPath);
             var objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            DaoException exception = new FailedFileOperationException(
                    resourceBundle.getString("FailedFileOperation"), e);
            logger.error(exception + resourceBundle.getString("cause"), exception.getCause());
        }
    }

    @Override
    public void close() {
        logger.info("FileSudokuBoardDao " + resourceBundle.getString("closed"));
    }

}

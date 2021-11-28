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

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final String filename;

    public FileSudokuBoardDao(String filename) {
        this.filename = filename;
    }

    public SudokuBoard read() {

        SudokuBoard sudokuBoard = null;

        try (var fileInputStream = new FileInputStream(filename);
             var objectInputStream = new ObjectInputStream(fileInputStream)) {
            sudokuBoard = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.print(e.toString());
        }
        return sudokuBoard;
    }

    public void write(SudokuBoard obj) {
        try (var fileOutputStream = new FileOutputStream(filename);
             var objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(e.toString());
        }
    }
}

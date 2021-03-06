/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

public final class SudokuBoardDaoFactory {

    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(final String filePath) {
        return new FileSudokuBoardDao(filePath);
    }

    public static Dao<SudokuBoard> getDatabaseDao(final JdbcSudokuBoardDao.BoardType boardType) {
        return new JdbcSudokuBoardDao(boardType);
    }
}


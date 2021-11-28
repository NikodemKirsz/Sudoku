/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    final String PATH_TO_FILE = "./files/fileSuccessfull";

    /*@Test
    void sudokuBoardDaoFactoryTest() {
        var sbdf = new SudokuBoardDaoFactory();
        assertNotNull(sbdf);
    }*/

    @Test
    void getFileDaoTest() {
        Dao<SudokuBoard> sbdao1 = SudokuBoardDaoFactory.getFileDao(PATH_TO_FILE);
        Dao<SudokuBoard> sbdao2 = SudokuBoardDaoFactory.getFileDao(PATH_TO_FILE);

        assertNotNull(sbdao1);
        assertTrue(sbdao1 instanceof Dao<SudokuBoard>);
        assertNotEquals(sbdao1, sbdao2);
        assertNotSame(sbdao1, sbdao2);
    }
}
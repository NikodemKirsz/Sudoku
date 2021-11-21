/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SudokuBoardTest {
    private final int size = 9;

    @Test
    void printBoardOut() {
        var sudoku = new SudokuBoard();
        sudoku.solveGame();
        sudoku.printBoard();
        assertEquals(1,1);
    }

    @Test
    void differentBoardsTest() {
        var board1 = new SudokuBoard();
        board1.solveGame();
        var board2 = new SudokuBoard();
        board2.solveGame();

        boolean different_boards = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!(board1.getBoard()[i][j] == board2.getBoard()[i][j])) {
                    different_boards = true;
                    break;
                }
            }
            if(different_boards) {
                break;
            }
        }
        assertTrue(different_boards);
    }

    @Test
    void getBoardTest() {
        var sudoku = new SudokuBoard();
        sudoku.getBoard()[0][0].setFieldValue(0);
        assertEquals(sudoku.getBoard()[0][0].getFieldValue(),0);
    }

    @Test
    void isValidTest() {
        int[][] validBoard  = new int[][]
                {
                        {5, 6, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 5, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        var validSudoku = new SudokuBoard(validBoard);
        boolean isSudokuValid = validSudoku.isBoardValid();
        assertTrue(isSudokuValid);

        // invalid arrays test
        var invalidBoardRow = new int[][]
                {
                        {5, 6, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        var invalidBoardColumn = new int[][]
                {
                        {5, 1, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        var invalidBoardBox = new int[][]
                {
                        {5, 6, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 5, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };

        int[][][] invalidBoards = {
                invalidBoardRow,
                invalidBoardColumn,
                invalidBoardBox
        };

        for (var invalidBoard : invalidBoards) {
            var invalidSudoku = new SudokuBoard(invalidBoard);
            isSudokuValid &= invalidSudoku.isBoardValid();
            assertFalse(isSudokuValid);
        }
    }

    @Test
    void getRowTest() {
        var firstRow = new int[] {5, 6, 1, 2, 3, 8, 4, 9, 7};
        var validBoard = new int[][]
                {
                        firstRow,
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 5, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        var sudokuBoard = new SudokuBoard(validBoard);

        var sudokuFieldsRowFirst = new SudokuField[size];
        for (int i = 0; i < size; i++) {
            sudokuFieldsRowFirst[i] = new SudokuField();
            sudokuFieldsRowFirst[i].setFieldValue(firstRow[i]);
        }

        var sudokuRowFirst = new SudokuRow();
        sudokuRowFirst.setSudokuFields(sudokuFieldsRowFirst);

        assertTrue(SudokuElement.equals(sudokuBoard.getRow(0), sudokuRowFirst));
        assertFalse(SudokuElement.equals(sudokuBoard.getRow(1), sudokuRowFirst));
    }

    @Test
    void getColumnTest() {
        var firstColumn = new int[] {5, 8, 2, 1, 3, 9, 6, 4, 7};
        var validBoard = new int[][]
                {
                        {firstColumn[0], 6, 1, 2, 3, 8, 4, 9, 7},
                        {firstColumn[1], 7, 4, 5, 6, 9, 2, 3, 1},
                        {firstColumn[2], 3, 9, 4, 7, 1, 6, 5, 8},
                        {firstColumn[3], 5, 8, 3, 2, 7, 9, 4, 6},
                        {firstColumn[4], 4, 7, 9, 5, 6, 8, 1, 2},
                        {firstColumn[5], 2, 6, 1, 8, 4, 3, 7, 5},
                        {firstColumn[6], 8, 3, 7, 9, 5, 1, 2, 4},
                        {firstColumn[7], 9, 5, 6, 1, 2, 7, 8, 3},
                        {firstColumn[8], 1, 2, 8, 4, 3, 5, 6, 9}
                };
        var sudokuBoard = new SudokuBoard(validBoard);

        var sudokuFieldsColumnFirst = new SudokuField[size];
        for (int i = 0; i < size; i++) {
            sudokuFieldsColumnFirst[i] = new SudokuField();
            sudokuFieldsColumnFirst[i].setFieldValue(firstColumn[i]);
        }

        var sudokuColumnFirst = new SudokuColumn();
        sudokuColumnFirst.setSudokuFields(sudokuFieldsColumnFirst);

        assertTrue(SudokuElement.equals(sudokuBoard.getColumn(0), sudokuColumnFirst));
        assertFalse(SudokuElement.equals(sudokuBoard.getColumn(1), sudokuColumnFirst));
    }

    @Test
    void getBoxTest() {
        var firstBox = new int[] {5, 6, 1, 8, 7, 4, 2, 3, 5};
        var validBoard = new int[][]
                {
                        {firstBox[0], firstBox[1], firstBox[2], 2, 3, 8, 4, 9, 7},
                        {firstBox[3], firstBox[4], firstBox[5], 5, 6, 9, 2, 3, 1},
                        {firstBox[6], firstBox[7], firstBox[8], 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        var sudokuBoard = new SudokuBoard(validBoard);

        var sudokuFieldsBoxFirst = new SudokuField[size];
        for (int i = 0; i < size; i++) {
            sudokuFieldsBoxFirst[i] = new SudokuField();
            sudokuFieldsBoxFirst[i].setFieldValue(firstBox[i]);
        }

        var sudokuBoxFirst = new SudokuBox();
        sudokuBoxFirst.setSudokuFields(sudokuFieldsBoxFirst);

        assertTrue(SudokuElement.equals(sudokuBoard.getBox(0, 0), sudokuBoxFirst));
        assertTrue(SudokuElement.equals(sudokuBoard.getBox(2, 0), sudokuBoxFirst));
        assertTrue(SudokuElement.equals(sudokuBoard.getBox(1, 2), sudokuBoxFirst));
        assertFalse(SudokuElement.equals(sudokuBoard.getBox(5, 4), sudokuBoxFirst));
        assertFalse(SudokuElement.equals(sudokuBoard.getBox(0, 8), sudokuBoxFirst));
    }

    private SudokuBoard firstSudokuBoard;
    private SudokuBoard sameSudokuBoard;
    private SudokuBoard differentSudokuBoard;

    @BeforeEach
    public void beforeEach()
    {
        var backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        var observer = new SudokuPlayer();
        var arr = new int[][]
                {
                        {5, 6, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 5, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        var differentArr = new int[][]
                {
                        {6, 6, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 5, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };

        firstSudokuBoard = new SudokuBoard(backtrackingSudokuSolver, observer);
        fillSudokuBoard(arr, firstSudokuBoard);

        sameSudokuBoard = new SudokuBoard(backtrackingSudokuSolver, observer);
        fillSudokuBoard(arr, sameSudokuBoard);

        differentSudokuBoard = new SudokuBoard(backtrackingSudokuSolver, observer);
        fillSudokuBoard(differentArr, differentSudokuBoard);
    }

    @Test
    public void equalsTest()
    {
        assertTrue(firstSudokuBoard.equals(firstSudokuBoard));
        assertTrue(firstSudokuBoard.equals(sameSudokuBoard));
        assertFalse(firstSudokuBoard.equals(differentSudokuBoard));
        assertFalse(firstSudokuBoard.equals(new SudokuPlayer()));

        // cohesion
        assertEquals(firstSudokuBoard.equals(differentSudokuBoard), firstSudokuBoard.equals(differentSudokuBoard));
    }

    @Test
    public void hashCodeTest()
    {
        assertEquals(firstSudokuBoard.hashCode(), sameSudokuBoard.hashCode());
        assertNotEquals(firstSudokuBoard.hashCode(), differentSudokuBoard.hashCode());

        // cohesion
        var hashCode = firstSudokuBoard.hashCode();
        assertEquals(hashCode, firstSudokuBoard.hashCode());
    }

    @Test
    public void toStringTest()
    {
        String info = firstSudokuBoard.toString();

        assertTrue(info.contains("SudokuBoard{"));
        assertTrue(info.contains("sudokuFields="));
        assertTrue(info.contains(", boardSize="));
        assertTrue(info.contains(", boxSize="));
        assertTrue(info.contains(", sudokuSolver="));
        assertTrue(info.contains(", observer="));
        assertTrue(info.contains("}"));
    }

    @AfterEach
    public void afterEach()
    {
        firstSudokuBoard = null;
        sameSudokuBoard = null;
        differentSudokuBoard = null;
    }

    private void fillSudokuBoard(int[][] differentArr, SudokuBoard differentSudokuBoard) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                differentSudokuBoard.set(i, j, differentArr[i][j]);
            }
        }
    }
}




















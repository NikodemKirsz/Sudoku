/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.first.firstjava;

public class SudokuField {
    private int value;

    SudokuField() {
        this.value = 0;
    }

    public int getFieldValue() {
        return value;
    }

    public boolean setFieldValue(int value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
            return true;
        }
        return false;
    }
}

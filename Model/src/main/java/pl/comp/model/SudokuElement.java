/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.NotEnoughElementsException;
import pl.comp.exceptions.SudokuException;

public class SudokuElement implements Serializable, Cloneable {
    private final List<SudokuField> sudokuFields;
    private static final int size = 9;
    private static final Logger logger = LoggerFactory.getLogger(SudokuElement.class);
    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("bundle");

    public SudokuElement() {
        this.sudokuFields = Arrays.asList(new SudokuField[size]);
        for (int i = 0; i < size; i++) {
            sudokuFields.set(i, new SudokuField());
        }
    }

    public boolean verify() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (sudokuFields.get(i).getFieldValue() == sudokuFields.get(j).getFieldValue()
                        || sudokuFields.get(i).getFieldValue() == 0
                        || sudokuFields.get(j).getFieldValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setSudokuFields(SudokuField[] sudokuFields) {
        if (sudokuFields.length != size) {
            throw new NotEnoughElementsException(resourceBundle.getString("NotEnoughElements"));
        }
        try {
            for (int i = 0; i < size; i++) {
                this.sudokuFields.get(i).setFieldValue(sudokuFields[i].getFieldValue());
            }
        } catch (SudokuException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    public List<SudokuField> getSudokuFields() {
        List<SudokuField> boardCopy = Arrays.asList(new SudokuField[size]);
        Collections.copy(boardCopy, sudokuFields);
        return boardCopy;
    }

    public static boolean equals(SudokuElement lhs, SudokuElement rhs) {
        var lhsFields = lhs.getSudokuFields();
        var rhsFields = rhs.getSudokuFields();

        boolean isEqual = true;
        for (var i = 0; i < size; i++) {
            isEqual &= lhsFields.get(i).getFieldValue() == rhsFields.get(i).getFieldValue();
        }
        return isEqual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuElement that)) {
            return false;
        }

        return new EqualsBuilder()
                .append(getSudokuFields(), that.getSudokuFields())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSudokuFields())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sudokuFields", sudokuFields)
                .toString();
    }

    @Override
    public SudokuElement clone() throws CloneNotSupportedException {
        SudokuElement clonedSudokuElement = new SudokuElement();

        SudokuField[] clonedSudokuFields = new SudokuField[size];
        for (int i = 0; i < size; i++) {
            clonedSudokuFields[i] = new SudokuField();
        }

        var index = 0;
        for (SudokuField field :
                sudokuFields) {
            clonedSudokuFields[index].setFieldValue(field.getFieldValue());
            index++;
        }
        clonedSudokuElement.setSudokuFields(clonedSudokuFields);
        return clonedSudokuElement;
    }
}

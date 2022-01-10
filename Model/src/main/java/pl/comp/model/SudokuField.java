/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import java.io.Serializable;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.comp.exceptions.IllegalBoardValueException;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int value;
    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("pl.comp.model.Bundle");

    SudokuField() {
        this.value = 0;
    }

    public int getFieldValue() {
        return value;
    }

    public boolean setFieldValue(int value) throws IllegalBoardValueException {
        if (value >= 0 && value <= 9) {
            this.value = value;
            return true;
        } else {
            throw new IllegalBoardValueException(resourceBundle.getString("IllegalBoardValue"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuField that)) {
            return false;
        }

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }

    @Override
    public int compareTo(SudokuField o) throws NullPointerException {
        if (o == null) {
            throw new NullPointerException();
        }
        return Integer.compare(getFieldValue(), o.getFieldValue());
    }
}

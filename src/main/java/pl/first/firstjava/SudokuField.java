/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.first.firstjava;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SudokuField that)) return false;

        return new EqualsBuilder()
                .append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}

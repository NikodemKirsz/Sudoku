/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import pl.comp.exceptions.DaoException;
import pl.comp.exceptions.FailedFileOperationException;

public interface Dao<T> {

    T read() throws DaoException;

    void write(T obj) throws DaoException;
}


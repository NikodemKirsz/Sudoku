<<<<<<< HEAD:src/main/java/pl/comp/IObserver.java
/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp;

public interface IObserver {
    String onValueChanged(boolean isValid);
}
=======
/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

public interface IObserver extends Cloneable {
    String onValueChanged(boolean isValid);
}
>>>>>>> origin/ViewFX:Model/src/main/java/pl/comp/model/IObserver.java

package pl.comp.model;

import java.util.ListResourceBundle;

public class Bundle_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"FailedFileOperation", "File operation failed."},
                {"IllegalBoardValue", "Wrong value was passed to the field."},
                {"NotEnoughElements", "Not enough elements in list."},
                {"cause", "\nCaused by"},
                {"closed", "closed"},
                {"max-min"}, {"Max must not be smaller than min!"}
        };
    }
}

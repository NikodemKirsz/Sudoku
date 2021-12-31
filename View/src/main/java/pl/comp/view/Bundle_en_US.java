package pl.comp.view;

import java.util.ListResourceBundle;

public class Bundle_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"welcomeText", "Welcome to the Sudoku Game!"},
                {"choiceLabel", "Choose the difficulty level"},
                {"easy", "Easy"},
                {"medium", "Medium"},
                {"hard", "Hard"},
                {"playButton", "Play"},
                {"winningLabel", "Congratulations, you won!"},
                {"saveButton", "Save"},
                {"readButton", "Load"},
                {"english", "English"},
                {"polish", "Polish"}
        };
    }
}

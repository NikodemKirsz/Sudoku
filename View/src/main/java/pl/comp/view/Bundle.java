package pl.comp.view;

import java.util.ListResourceBundle;

public class Bundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"welcomeText", "Witaj w grze Sudoku!"},
                {"choiceLabel", "Wybierz poziom trudności"},
                {"easy", "Łatwy"},
                {"medium", "Średni"},
                {"hard", "Trudny"},
                {"playButton", "Graj"},
                {"winningLabel", "Brawo, wygrałeś!"},
                {"saveButton", "Zapisz"},
                {"readButton", "Wczytaj"},
                {"english", "Angielski"},
                {"polish", "Polski"}
        };
    }
}

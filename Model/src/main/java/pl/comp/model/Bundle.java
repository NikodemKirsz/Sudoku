package pl.comp.model;

import java.util.ListResourceBundle;

public class Bundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"FailedFileOperation", "Operacja na pliku nie powiodła się."},
                {"IllegalBoardValue", "Przekazana została zła wartość."},
                {"NotEnoughElements", "Niewystarczająca liczba elementów listy."},
                {"cause", "\nSpowodowany przez"},
                {"closed", "zamkniety"},
                {"max-min", "Wartość maksymalna nie może być mniejsza od minimalnej."}
        };
    }
}

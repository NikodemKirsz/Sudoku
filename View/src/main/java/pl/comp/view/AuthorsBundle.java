package pl.comp.view;

import java.util.ListResourceBundle;

public class AuthorsBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"title", "Autorzy"},
                {"first_author", "Nikodem Kirsz"},
                {"second_author", "Oskar Trela"}
        };
    }
}

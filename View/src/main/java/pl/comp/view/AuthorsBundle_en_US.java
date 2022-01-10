package pl.comp.view;

import java.util.ListResourceBundle;

public class AuthorsBundle_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"title", "Authors"},
                {"first_author", "Nikodem Kirch"},
                {"second_author", "Oscar Trela"}
        };
    }
}

package pl.comp.view;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleManager {

    public static ResourceBundle changeLanguage(Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("pl.comp.view.Bundle", locale);
        return resourceBundle;
    }
}

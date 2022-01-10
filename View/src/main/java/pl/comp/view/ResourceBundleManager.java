package pl.comp.view;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleManager {

    public static ResourceBundle changeLanguage(Locale locale) {
        return ResourceBundle.getBundle("viewBundle", locale);
    }
}

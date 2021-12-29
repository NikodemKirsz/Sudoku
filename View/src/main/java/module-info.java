module pl.comp.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires pl.comp.model;
    requires java.logging;


    opens pl.comp.view to javafx.fxml;
    exports pl.comp.view;
}
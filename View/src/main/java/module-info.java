module pl.comp.view {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.comp.view to javafx.fxml;
    exports pl.comp.view;
}
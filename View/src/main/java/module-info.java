module pl.comp.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires pl.comp.model;
    requires java.logging;
    requires org.slf4j;

    opens pl.comp.view to javafx.fxml;
    exports pl.comp.view;
}
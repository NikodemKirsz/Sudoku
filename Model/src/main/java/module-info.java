module pl.comp.model {
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires java.sql;

    exports pl.comp.model;
    opens pl.comp.model;
    exports pl.comp.exceptions;
}
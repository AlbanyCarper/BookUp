module com.carper.bookup {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.carper.bookup to javafx.fxml;
    exports com.carper.bookup;
    exports com.carper.bookup.controller;
    opens com.carper.bookup.controller to javafx.fxml;
    opens com.carper.bookup.base to javafx.fxml;
    exports com.carper.bookup.base;
}
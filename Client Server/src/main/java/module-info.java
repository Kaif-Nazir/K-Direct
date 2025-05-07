module com.clientserver.clientserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;


    opens com.clientserver.clientserver to javafx.fxml;
    exports com.clientserver.clientserver;
}
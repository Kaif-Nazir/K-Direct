module com.safexbank.clientmessenger {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;


    opens com.clientmessenger.clientmessenger to javafx.fxml;
    exports com.clientmessenger.clientmessenger;
}
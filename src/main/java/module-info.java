module com.example.bty {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;

    opens com.example.bty to javafx.fxml;
    exports com.example.bty;
}
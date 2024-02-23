module com.example.bty {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;

    opens com.example.bty to javafx.fxml;
    exports com.example.bty;
    exports com.example.bty.Controllers;
    opens com.example.bty.Controllers to javafx.fxml;
    exports com.example.bty.Controllers.cours;
    opens com.example.bty.Controllers.cours to javafx.fxml;
}
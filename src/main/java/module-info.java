module com.example.bty {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
exports com.example.bty.Controllers.graphiqueGCP;
    opens com.example.bty to javafx.fxml;
    exports com.example.bty;
    exports com.example.bty.Controllers;
    opens com.example.bty.Controllers to javafx.fxml;
    exports Controllers;
    opens Controllers to javafx.fxml;


}
module com.example.bty {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    opens com.example.bty.Entities to javafx.base;

    opens com.example.bty to javafx.fxml;
    exports com.example.bty;
    opens com.example.bty.Controllers to javafx.fxml;
    opens com.example.bty.Controllers.EvenementController to javafx.fxml;
    exports com.example.bty.Controllers.EvenementController;



}
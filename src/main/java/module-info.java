module com.example.bty {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    opens com.example.bty.Entities to javafx.base;
    requires javafx.web;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.prefs;

    opens com.example.bty to javafx.fxml;
    exports com.example.bty;
    exports com.example.bty.Controllers;
    exports com.example.bty.Controllers.Admin;
    exports com.example.bty.Controllers.Coach;
    exports com.example.bty.Entities;
    exports com.example.bty.Services;
    opens com.example.bty.Controllers to javafx.fxml;
    opens com.example.bty.Controllers.EvenementController to javafx.fxml;
    exports com.example.bty.Controllers.EvenementController;



    exports com.example.bty.Views;
    opens com.example.bty.Views to javafx.fxml;

}
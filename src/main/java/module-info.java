module com.example.bty {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    requires javafx.web;
    requires java.desktop;
    requires java.mail;
    requires totp;

    opens com.example.bty to javafx.fxml;
    exports com.example.bty;
    exports com.example.bty.Controllers;
    exports com.example.bty.Entities;
    exports com.example.bty.Services;
    opens com.example.bty.Controllers to javafx.fxml;
    exports com.example.bty.views;
    opens com.example.bty.views to javafx.fxml;

}
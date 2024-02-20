module com.example.bty {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    requires javafx.web;
    requires itextpdf;

    opens com.example.bty to javafx.fxml;
    exports com.example.bty;
    exports com.example.bty.Controllers;
    exports com.example.bty.Controllers.Admin;
    exports com.example.bty.Controllers.Coach;
    exports com.example.bty.Entities;
    exports com.example.bty.Services;
    opens com.example.bty.Controllers to javafx.fxml;
    exports com.example.bty.Controllers.ProduitController;
    opens com.example.bty.Controllers.ProduitController to javafx.fxml;



}
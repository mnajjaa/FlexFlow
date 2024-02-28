package com.example.bty;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginView extends Application {
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws Exception {
     //   FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("/LoginGym.fxml"));
           FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("/LoginGym.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("FitHub Pro");

        stage.setResizable(false);


        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent event) ->{
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        // Charger votre fichier CSS si nécessaire
//         scene.getStylesheets().add(getClass().getResource("votreCheminVersCSS.css").toExternalForm());


    }

    public static void main(String[] args) {
        launch(args);
    }

}

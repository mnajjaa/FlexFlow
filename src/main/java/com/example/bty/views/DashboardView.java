package com.example.bty.views;

import com.example.bty.LoginView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("/membreList.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("FitHub Pro");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

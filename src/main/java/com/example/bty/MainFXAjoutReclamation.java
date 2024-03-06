package com.example.bty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainFXAjoutReclamation extends Application {
    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/Fxml/FxmlReclamation/AjouterReclamation.fxml"));
//        Parent root=fxmlLoader.load();
//        Scene scene= new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("Ajouter reclamation");
//        stage.show();


        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/Fxml/FxmlReponse/AjouterReponse.fxml"));
        Parent root=fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ajouter reponse");
        stage.show();


    }
    public static void main (String[] args){
        launch(args);
    }
}



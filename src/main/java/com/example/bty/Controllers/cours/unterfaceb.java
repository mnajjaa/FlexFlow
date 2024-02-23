package com.example.bty.Controllers.cours;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class unterfaceb extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formulaire Nom Prénom");

        // Création des champs de texte
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        nomField.getStyleClass().add("text-field");

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        prenomField.getStyleClass().add("text-field");

        // Création du bouton
        Button boutonValider = new Button("Valider");
        boutonValider.setOnAction(e -> {
            // Action à effectuer lors du clic sur le bouton (peut être vide pour le moment)
            System.out.println("Nom: " + nomField.getText());
            System.out.println("Prénom: " + prenomField.getText());
        });
        boutonValider.getStyleClass().add("button");

        // Mise en page
        VBox vbox = new VBox(10, nomField, prenomField, boutonValider);
        vbox.getStyleClass().add("vbox");
        vbox.setPadding(new Insets(20));

        // Création de la scène
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/styles.css").toExternalForm());



        // scene.getStylesheets().add(getClass().getResource("com/example/bty/resources/styles.css").toExternalForm());
       // Parent root= FXMLLoader.load(getClass().getResource("/Afficher.fxml"));


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

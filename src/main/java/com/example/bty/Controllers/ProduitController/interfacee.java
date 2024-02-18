package com.example.bty.Controllers.ProduitController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class interfacee extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formulaire Nom Prénom");

        // Création des champs de texte
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");

        // Création du bouton
        Button boutonValider = new Button("Valider");
        boutonValider.setOnAction(e -> {
            // Action à effectuer lors du clic sur le bouton (peut être vide pour le moment)
            System.out.println("Nom: " + nomField.getText());
            System.out.println("Prénom: " + prenomField.getText());
        });

        // Mise en page
        VBox vbox = new VBox(10, nomField, prenomField, boutonValider);
        vbox.setPadding(new Insets(20));
        vbox.getStyleClass().add("vbox"); // Appliquer la classe CSS

        // Création de la scène
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 300, 200);

        // Charger le fichier CSS
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/stylee.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

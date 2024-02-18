package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccepterRefuserDemandes extends Application {


    public void start(Stage primaryStage) {
        primaryStage.setTitle("Accepter ou Refuser Demandes");

        // Création de la liste des demandes (simulée avec des chaînes)
        ListView<String> demandesListView = new ListView<>();
        demandesListView.setItems(FXCollections.observableArrayList(
                "Demande 1", "Demande 2", "Demande 3", "Demande 4", "Demande 5"));

        // Boutons Accepter et Refuser
        Button accepterButton = new Button("Accepter");
        Button refuserButton = new Button("Refuser");

        // Gestion des événements pour les boutons
        accepterButton.setOnAction(e -> {
            String selectedDemande = demandesListView.getSelectionModel().getSelectedItem();
            if (selectedDemande != null) {
                System.out.println("Demande acceptée : " + selectedDemande);
                // Vous pouvez ajouter ici le code pour traiter la demande acceptée
            }
        });

        refuserButton.setOnAction(e -> {
            String selectedDemande = demandesListView.getSelectionModel().getSelectedItem();
            if (selectedDemande != null) {
                System.out.println("Demande refusée : " + selectedDemande);
                // Vous pouvez ajouter ici le code pour traiter la demande refusée
            }
        });

        // Mise en page des boutons
        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(accepterButton, refuserButton);

        // Mise en page principale
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(demandesListView, buttonsBox);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
        //liaison decoration avec code javafx

    }

    public static void main(String[] args) {
        launch(args);
    }

}

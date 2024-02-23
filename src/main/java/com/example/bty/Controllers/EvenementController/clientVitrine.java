package com.example.bty.Controllers.EvenementController;

import com.example.bty.Entities.Evenement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clientVitrine extends Application {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pidevgym";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vitrine Client");

        // Récupérer la liste des événements (vous devez implémenter cette méthode)
        List<Evenement> evenements = getListeEvenements();

        // Créer une VBox pour afficher les cartes d'événements

//        HBox hbox = new HBox(10);
//        hbox.setPadding(new Insets(10));
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_LEFT);
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(10));
        // Parcourir la liste des événements et créer une carte pour chaque événement
        for (Evenement evenement : evenements) {
//            VBox carte = createEventCard(evenement);
//            vbox.getChildren().add(carte);
//            VBox carte = createEventCard(evenement);
//            hbox.getChildren().add(carte);
            VBox carte = createEventCard(evenement);
            flowPane.getChildren().add(carte);
        }

        Scene scene = new Scene(flowPane, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/CSSmoduleEvenement/VitrineClient.css").toExternalForm());

    }

    // Méthode pour créer une carte d'événement
//    private VBox createEventCard(Evenement evenement) {
//        VBox carte = new VBox(10);
//        carte.setPadding(new Insets(10));
//
//        // Afficher l'image de l'événement
//        ByteArrayInputStream stream = new ByteArrayInputStream(evenement.getImage());
//        Image image = new Image(stream);
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(200);
//        imageView.setFitHeight(150);
//
//        // Afficher le nom de l'événement
//        carte.getChildren().add(new ImageView(new Image(new ByteArrayInputStream(evenement.getImage()))));
//        carte.getChildren().add(new javafx.scene.control.Label("Nom: " + evenement.getNom()));
//
//        // Ajouter un bouton "See More"
//        Button seeMoreButton = new Button("See More");
//        seeMoreButton.setOnAction(e -> afficherPlusDeDetails(evenement));
//        carte.getChildren().add(seeMoreButton);
//
//        return carte;
//    }
    private VBox createEventCard(Evenement evenement) {
//        VBox carte = new VBox(10);
//        carte.setPadding(new Insets(10));
        VBox card = new VBox();
        card.getStyleClass().add("product-card");
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(10);
        card.setPrefWidth(220); // Définir une largeur préférée fixe
        card.setMinHeight(250);

        // Afficher l'image de l'événement
        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(evenement.getImage())));

        imageView.setFitWidth(200);
        imageView.setFitHeight(170);

        // Afficher le nom de l'événement
        Label nomLabel = new Label( evenement.getNom());
        nomLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;-fx-text-fill: #A62609;");



        // Ajouter un bouton "See More"
        Button ReserverButton = new Button("Reserver");
        ReserverButton.getStyleClass().add("add-to-cart-button");

        ReserverButton.setOnAction(e -> ReserverEvenement(evenement));

        // Ajouter les éléments à la carte
        card.getChildren().addAll(imageView, nomLabel, ReserverButton);

        return card;
    }


    // Méthode pour afficher plus de détails sur l'événement (vous devez implémenter cette méthode)
    private void ReserverEvenement(Evenement evenement) {
        // Implémentez le code pour afficher plus de détails sur l'événement
        // Vous pouvez utiliser une nouvelle fenêtre ou un panneau pour afficher les détails
    }

    private List<Evenement> getListeEvenements() {
        List<Evenement> evenements = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM evenement";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id_evenement");
                    String nom = resultSet.getString("nomEvenement");
                    byte[] imageBytes = resultSet.getBytes("image");

                    try {
                        Evenement evenement = new Evenement(id, nom, imageBytes);
                        evenements.add(evenement);
                    } catch (Exception e) {
                        System.err.println("Erreur lors de la conversion de l'image pour l'événement avec l'ID : " + id);
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evenements;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

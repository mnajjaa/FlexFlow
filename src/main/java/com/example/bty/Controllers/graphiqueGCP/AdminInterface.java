package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class AdminInterface extends Application {

    private static Connection connection;
    private static ListView<OffreItem> offresListView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Accepter ou Refuser Offres");

        // Connexion à la base de données
        connectToDatabase();

        // Création de la liste des offres
        offresListView = new ListView<>();
        refreshOffresList();

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().add(offresListView);

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/Styles/StyleAR.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/pidevgym";
            String utilisateur = "root";
            String motDePasse = "";

            connection = DriverManager.getConnection(url, utilisateur, motDePasse);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion
        }
    }

    private static void refreshOffresList() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM offre");
            ResultSet resultSet = statement.executeQuery();
            offresListView.getItems().clear();

            while (resultSet.next()) {
                OffreItem offreItem = new OffreItem(
                        resultSet.getString("id"),
                        resultSet.getString("specialite"),
                        resultSet.getFloat("tarif_heure"),
                        resultSet.getString("id_coach")
                );

                offresListView.getItems().add(offreItem);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de requête
        }
    }

    public static class OffreItem extends HBox {
        public OffreItem(String id, String specialite, float tarif_heure, String id_coach) {
            super();

            Label label = new Label("ID offre : " + id + ", Spécialité : " + specialite +
                    ", Tarif par heure : " + tarif_heure + ", ID coach : " + id_coach);

            Button accepterButton = new Button("Accepter");
            Button refuserButton = new Button("Refuser");
            Button modifierButton = new Button("Modifier");

            accepterButton.setOnAction(e -> accepterOffre(id));
            refuserButton.setOnAction(e -> refuserOffre(id));
            modifierButton.setOnAction(e -> modifierOffre(id));

            this.getChildren().addAll(label, accepterButton, refuserButton, modifierButton);
            this.setSpacing(10);
        }
    }

    private static void accepterOffre(String id) {
        // Implémenter la logique pour accepter une offre
    }

    private static void refuserOffre(String id) {
        // Implémenter la logique pour refuser une offre
    }

    private static void modifierOffre(String id) {
        // Implémenter la logique pour modifier une offre
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationOffre1 extends Application {

    private static Connection connection;
    private static TableView<OffreItem> tableView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Consultation des offres");

        // Connexion à la base de données
        connectToDatabase();

        // Création de la table des offres sous forme de TableView
        tableView = new TableView<>();

        // Création du TableView et de ses colonnes
        TableColumn<OffreItem, String> nomCol = new TableColumn<>("Description d'offre");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<OffreItem, String> specialiteCol = new TableColumn<>("Spécialité");
        specialiteCol.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        TableColumn<OffreItem, String> tarifCol = new TableColumn<>("Tarif");
        tarifCol.setCellValueFactory(new PropertyValueFactory<>("tarif"));

        TableColumn<OffreItem, String> coachCol = new TableColumn<>("Coach");
        coachCol.setCellValueFactory(new PropertyValueFactory<>("coach"));

        TableColumn<OffreItem, String> etatCol = new TableColumn<>("État");
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));

        // Ajouter les colonnes au TableView
        tableView.getColumns().addAll(nomCol, specialiteCol, tarifCol, coachCol, etatCol);

        // Charger les données dans le TableView
        try {
            tableView.getItems().addAll(retrieveOffreItemsArray());
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de récupération des données
        }

        // Création de la disposition verticale
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.getChildren().add(tableView);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void connectToDatabase() {
        try {
            // Charger le pilote JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données MySQL
            String url = "jdbc:mysql://localhost:3306/pidevgym";
            String utilisateur = "root";
            String motDePasse = "";

            connection = DriverManager.getConnection(url, utilisateur, motDePasse);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion
        }
    }

    // Méthode pour récupérer les offres depuis la base de données et les placer dans un tableau
    private List<OffreItem> retrieveOffreItemsArray() throws SQLException {
        List<OffreItem> offresList = new ArrayList<>();
        try {
            // Exécuter une requête pour récupérer les offres depuis la base de données
            String query = "SELECT * FROM Offre";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats de la requête
            while (resultSet.next()) {
                String etat = resultSet.getString("etatOffre");
                // Vérifier si l'état est "acceptée"
                if ("acceptée".equalsIgnoreCase(etat)) {
                    // Créer un objet OffreItem pour chaque ligne de la base de données
                    OffreItem offreItem = new OffreItem(
                            resultSet.getString("nom"),
                            resultSet.getString("Specialite"),
                            resultSet.getString("tarif_heure"),
                            resultSet.getString("id_Coach"),
                            etat
                    );
                    offresList.add(offreItem); // Ajouter l'objet à la liste
                }
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de requête
            throw e;
        }

        return offresList;
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Classe interne représentant un élément d'offre
    public static class OffreItem {
        private String nom;
        private String specialite;
        private String tarif;
        private String coach;
        private String etat;

        public OffreItem(String nom, String specialite, String tarif, String coach, String etat) {
            this.nom = nom;
            this.specialite = specialite;
            this.tarif = tarif;
            this.coach = coach;
            this.etat = etat;
        }

        // Getters pour accéder aux champs de l'offre
        public String getNom() {
            return nom;
        }

        public String getSpecialite() {
            return specialite;
        }

        public String getTarif() {
            return tarif;
        }

        public String getCoach() {
            return coach;
        }

        public String getEtat() {
            return etat;
        }
    }
}

package com.example.bty.Controllers.graphiqueGCP;

import com.example.bty.Entities.Demande;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;

public class ConsultationDemandes extends Application {

    private static Connection connection;
    private static ListView<DemandeItem> demandesListView;
    private static String nom; // Ajouter un champ pour stocker le nom du client

    public ConsultationDemandes(String nom) {
        this.nom = nom;
    }

    private List<Demande> demandes;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Consultation des demandes");

        // Connexion à la base de données
        connectToDatabase();

        // Création de la liste des demandes
        demandesListView = new ListView<>();
        refreshDemandesList(nom);

        // Création de la disposition verticale
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.getChildren().add(demandesListView);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToDatabase() {
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

    // Méthode pour récupérer les demandes depuis la base de données
    private static void refreshDemandesList(String nomClient) {
        try {
            // Exécuter une requête pour récupérer les demandes du client depuis la base de données
            String query = "SELECT * FROM Demande WHERE nom = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomClient);
            ResultSet resultSet = statement.executeQuery();

            // Effacer la liste des demandes précédentes
            demandesListView.getItems().clear();

            // Ajouter les demandes à la liste
            while (resultSet.next()) {
                DemandeItem demandeItem = new DemandeItem(
                        resultSet.getString("nom"),
                        resultSet.getString("id_demande"),
                        resultSet.getString("but"),
                        resultSet.getString("NiveauPhysique"),
                        resultSet.getString("MaladieChronique"),
                        resultSet.getString("age"),
                        resultSet.getString("id_user"),
                        resultSet.getString("id_offre"),
                        resultSet.getString("etat"),
                        resultSet.getString("nombreHeure"),
                        resultSet.getTime("horaire"),
                        resultSet.getString("lesjours")
                );

                demandesListView.getItems().add(demandeItem);
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de requête
        }
    }

    // Méthode pour supprimer une demande
    private static void supprimerDemande(String id_demande, String nomClient) {
        try {
            // Créer la requête SQL pour supprimer la demande
            String query = "DELETE FROM Demande WHERE id_demande = ? AND nom = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id_demande); // Utilisation de l'ID de demande passé en paramètre
            statement.setString(2, nomClient); // Ajout du nom du client dans la clause WHERE
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                // Si la demande est supprimée avec succès, rafraîchir la liste des demandes
                refreshDemandesList(nomClient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de suppression de demande
        }
    }
    // Méthode pour modifier les jours d'une demande
    private static void modifierDemande(String id_demande) {
        // Implémentez ici la logique pour permettre à l'utilisateur de modifier  la demande
        System.out.println("Modifierla demande : " + id_demande);
    }

    public static class DemandeItem extends HBox {
        public DemandeItem(String nom, String id_demande, String but, String NiveauPhysique, String MaladieChronique,
                           String age, String id_user, String id_offre, String etat, String nombreHeure, Time horaire, String lesjours) {
            super();

            Label label = new Label("ID demande : " + id_demande + ", But : " + but + ", Niveau physique : " +
                    NiveauPhysique + ", Maladie chronique : " + MaladieChronique + ", Age : " + age +
                    ", ID utilisateur : " + id_user + ", ID offre : " + id_offre + ", État : " + etat + ", nombreHeure : " + nombreHeure + ", Horaire : " + horaire + ", Lesjours : " + lesjours+", Nom : " + nom);

            Button supprimerButton = new Button("Supprimer");
            supprimerButton.setOnAction(event -> {
                ConsultationDemandes.DemandeItem demandeItem = (ConsultationDemandes.DemandeItem) supprimerButton.getParent();
                String id_demandeToDelete = demandeItem.getId_demande();

                // Passer le nom du client à la méthode supprimerDemande
                supprimerDemande(id_demandeToDelete, ConsultationDemandes.nom);
            });

            Button modifierButton = new Button("Modifier ");
            modifierButton.setOnAction(event -> modifierDemande(id_demande)); // Passer la demande à la méthode modifierJoursDemande

            this.getChildren().addAll(label, supprimerButton, modifierButton);
            this.setSpacing(10);
        }

        public String getId_demande() {
            return ((Label) this.getChildren().get(0)).getText().replace("ID demande : ", "");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
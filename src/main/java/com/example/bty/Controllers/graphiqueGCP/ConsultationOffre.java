package com.example.bty.Controllers.graphiqueGCP;

import com.example.bty.Entities.Demande;
import com.example.bty.Entities.Offre;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;
    public class ConsultationOffre extends Application {

        private static Connection connection;
        public static ListView<com.example.bty.Controllers.graphiqueGCP.ConsultationOffre.OffreItem> OffreListView;
        private static int id;

        public ConsultationOffre(int id) {
            this.id = id;
        }

        private List<Offre> offre;


        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Consultation des demandes");

            // Connexion à la base de données
            connectToDatabase();

            // Création de la liste des demandes
            OffreListView = new ListView<>();
            refreshOffreList(id);

            // Création de la disposition verticale
            VBox vbox = new VBox();
            vbox.setSpacing(20);
            vbox.getChildren().add(OffreListView);

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

        // Méthode pour récupérer les demandes depuis la base de données
        private static void refreshOffreList(int id) {
            try {
                // Exécuter une requête pour récupérer les demandes du client depuis la base de données
                String query = "SELECT * FROM Offre WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, id); // Utiliser setInt pour l'ID au lieu de setString
                ResultSet resultSet = statement.executeQuery();

                // Effacer la liste des offres précédentes
                OffreListView.getItems().clear();

                // Ajouter les offres dans la liste
                while (resultSet.next()) {
                    ConsultationOffre.OffreItem offreItem = new ConsultationOffre.OffreItem(
                            resultSet.getString("id"),
                            resultSet.getString("Specialite"),
                            resultSet.getString("tarif_heure"),
                            resultSet.getString("id_Coach"),
                            resultSet.getString("etatOffre")
                    );
                    OffreListView.getItems().add(offreItem); // Ajouter l'élément à la liste
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
                    refreshOffreList(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les erreurs de suppression de demande
            }


        }

        private static void mettreAJourOffre(String id, String nouvelleSpecialite, String nouveauTarif, String nouveauCoach) {
            try {
                // Établir une connexion à la base de données
                connectToDatabase();

                // Exécuter une requête pour mettre à jour les détails de l'offre
                String query = "UPDATE Offre SET Specialite = ?, tarif_heure = ?, id_Coach = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nouvelleSpecialite);
                statement.setString(2, nouveauTarif);
                statement.setString(3, nouveauCoach);
                statement.setString(4, id);
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("L'offre a été mise à jour avec succès.");
                } else {
                    System.out.println("Impossible de mettre à jour l'offre.");
                }

                // Fermer les ressources
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les erreurs de mise à jour de l'offre
            }
        }
        private static void modifierOffre(String id) {
            // Créer une nouvelle fenêtre de modification de l'offre
            Stage modifierStage = new Stage();
            modifierStage.setTitle("Modifier Offre");

            // Créer des champs de saisie pour les détails de l'offre
            TextField specialiteField = new TextField();
            TextField tarifField = new TextField();
            TextField coachField = new TextField();

            // Créer un bouton pour enregistrer les modifications
            Button saveButton = new Button("Enregistrer");
            saveButton.setOnAction(event -> {
                // Récupérer les nouvelles valeurs saisies par l'utilisateur
                String nouvelleSpecialite = specialiteField.getText();
                String nouveauTarif = tarifField.getText();
                String nouveauCoach = coachField.getText();

                // Mettre à jour les informations de l'offre dans la base de données
                // (vous devez implémenter cette fonctionnalité)
                mettreAJourOffre(id, nouvelleSpecialite, nouveauTarif, nouveauCoach);

                // Rafraîchir la liste des offres pour refléter les modifications
                refreshOffreList(Integer.parseInt(id));

                // Fermer la fenêtre de modification de l'offre
                modifierStage.close();
            });

            // Créer une disposition pour organiser les champs de saisie et le bouton
            VBox vbox = new VBox();
            vbox.getChildren().addAll(
                    new Label("Specialité:"),
                    specialiteField,
                    new Label("Tarif:"),
                    tarifField,
                    new Label("Coach:"),
                    coachField,
                    saveButton
            );
            vbox.setSpacing(10);

            // Créer une scène pour la fenêtre de modification de l'offre
            Scene scene = new Scene(vbox, 300, 200);
            modifierStage.setScene(scene);
            modifierStage.show();
        }
        public static class OffreItem extends HBox {
            private TextField idField;
            private ChoiceBox<String> specialiteChoice;
            private TextField tarifField;
            private TextField coachField;

            public OffreItem(String id, String specialite, String tarif, String coach, String etat)
                              {
                super();

                Label label = new Label("ID  : " + id+ ", SpecialiteChoice : " + specialite +
                         ", Tarif : "  +tarif +  ",coach  : "  + coach +   ", Etat : " + etat );


                Button supprimerButton = new Button("Supprimer");
                supprimerButton.setOnAction(event -> {
                    com.example.bty.Controllers.graphiqueGCP.ConsultationOffre.OffreItem offreItem = (OffreItem) supprimerButton.getParent();
                    String idToDelete = offreItem.getId();

                    // Passer le nom du client à la méthode supprimerDemande
                    supprimerDemande(idToDelete, String.valueOf(ConsultationOffre.id));
                });

                Button modifierButton = new Button("Modifier ");
                modifierButton.setOnAction(event -> modifierOffre(id)); // Passer la demande à la méthode modifierJoursDemande

                this.getChildren().addAll(label, supprimerButton, modifierButton);
                this.setSpacing(10);
            }

            public String getId_demande() {
                return ((Label) this.getChildren().get(0)).getText().replace("ID  : ", "");
            }

        }

        public static void main(String[] args) {
            launch(args);
        }
    }



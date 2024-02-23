package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class AccepterRefuserDemandes extends Application {

    private static Connection connection;
    private static ListView<DemandeItem> demandesListView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Accepter ou Refuser Demandes");

        // Connexion à la base de données
        connectToDatabase();

        // Création de la liste des demandes
        demandesListView = new ListView<>();
        refreshDemandesList();

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().add(demandesListView);

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/Styles/StyleAR.css").toExternalForm());
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

    private static void refreshDemandesList() {
        try {
            // Exécuter une requête pour récupérer les demandes depuis la base de données
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM demande");
            ResultSet resultSet = statement.executeQuery();

            // Effacer la liste des demandes précédentes
            demandesListView.getItems().clear();

            // Ajouter les demandes à la liste
            while (resultSet.next()) {
                DemandeItem demandeItem = new DemandeItem(
                        resultSet.getString("id_demande"),
                        resultSet.getString("but"),
                        resultSet.getString("NiveauPhysique"),
                        resultSet.getString("MaladieChronique"),
                        resultSet.getString("age"),
                        resultSet.getString("id_user"),
                        resultSet.getString("id_offre"),
                        resultSet.getString("etat"),
                        resultSet.getTime("horaire"), // Récupérer l'horaire depuis la base de données
                        resultSet.getString("lesjours") // Récupérer les jours depuis la base de données
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

    public static class DemandeItem extends HBox {
        public DemandeItem(String id_demande, String but, String NiveauPhysique, String MaladieChronique,
                           String age, String id_user, String id_offre, String etat, Time horaire, String lesjours) {
            super();

            Label label = new Label("ID demande : " + id_demande + ", But : " + but + ", Niveau physique : " +
                    NiveauPhysique + ", Maladie chronique : " + MaladieChronique + ", Age : " + age +
                    ", ID utilisateur : " + id_user + ", ID offre : " + id_offre + ", État : " + etat+ ",Horaire :"  +horaire +",Lesjours :" +lesjours );

            Button accepterButton = new Button("Accepter");
            Button refuserButton = new Button("Refuser");
            Button modifierJoursButton = new Button("Modifier les jours");

            // Gestion des événements pour les boutons
            accepterButton.setOnAction(e -> accepterDemande(id_demande));
            refuserButton.setOnAction(e -> refuserDemande(id_demande));
            modifierJoursButton.setOnAction(e -> afficherInterfaceModifierJours(id_demande));

            this.getChildren().addAll(label, accepterButton, refuserButton, modifierJoursButton);
            this.setSpacing(10);
        }
    }

    private static void accepterDemande(String id_demande) {
        try {
            // Modifier l'état dans la base de données
            PreparedStatement statement = connection.prepareStatement("UPDATE demande SET etat = 'Acceptée' WHERE id_demande = ?");
            statement.setString(1, id_demande);
            statement.executeUpdate();
            statement.close();

            // Afficher une interface pour ajouter l'horaire et autre information
            afficherInterfaceAjoutHoraire(id_demande);

            // Afficher un message au client (demande acceptée)
            afficherMessageClientAccepte();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de base de données
        }
    }

    private static void refuserDemande(String id_demande) {
        try {
            // Modifier l'état dans la base de données
            PreparedStatement statement = connection.prepareStatement("UPDATE demande SET etat = 'Refusée' WHERE id_demande = ?");
            statement.setString(1, id_demande);
            statement.executeUpdate();
            statement.close();

            // Afficher un message au client (demande refusée)
            afficherMessageClientRefuse();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de base de données
        }
    }

    private static void afficherInterfaceAjoutHoraire(String id_demande) {
        Stage ajoutHoraireStage = new Stage();
        ajoutHoraireStage.setTitle("Ajouter Horaire");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        Label label = new Label("Entrez l'horaire :");
        TextField horaireField = new TextField();

        Button ajouterButton = new Button("Ajouter");
        ajouterButton.setOnAction(e -> {
            try {
                // Ajouter l'horaire dans la base de données
                PreparedStatement statement = connection.prepareStatement("UPDATE demande SET horaire = ? WHERE id_demande = ?");
                statement.setString(1, horaireField.getText());
                statement.setString(2, id_demande);
                statement.executeUpdate();
                statement.close();

                ajoutHoraireStage.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Gérer les erreurs de base de données
            }
        });

        vbox.getChildren().addAll(label, horaireField, ajouterButton);

        Scene scene = new Scene(vbox, 300, 150);
        ajoutHoraireStage.setScene(scene);
        ajoutHoraireStage.show();
    }

    private static void afficherInterfaceModifierJours(String id_demande) {
        Stage modifierJoursStage = new Stage();
        modifierJoursStage.setTitle("Modifier les jours");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        Label label = new Label("Entrez les nouveaux jours :");
        TextField joursField = new TextField();

        Button modifierButton = new Button("Modifier");
        modifierButton.setOnAction(e -> {
            try {
                // Mettre à jour les jours dans la base de données
                PreparedStatement statement = connection.prepareStatement("UPDATE demande SET lesjours = ? WHERE id_demande = ?");
                statement.setString(1, joursField.getText());
                statement.setString(2, id_demande);
                statement.executeUpdate();
                statement.close();

                modifierJoursStage.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Gérer les erreurs de base de données
            }
        });

        vbox.getChildren().addAll(label, joursField, modifierButton);

        Scene scene = new Scene(vbox, 300, 150);
        modifierJoursStage.setScene(scene);
        modifierJoursStage.show();
    }

    private static void afficherMessageClientAccepte() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Demande Acceptée");
        alert.setHeaderText(null);
        alert.setContentText("Votre demande est acceptée.");
        alert.showAndWait();
    }

    private static void afficherMessageClientRefuse() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Demande Refusée");
        alert.setHeaderText(null);
        alert.setContentText("Votre demande est refusée.");
        alert.showAndWait();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // Fermer la connexion à la base de données lors de l'arrêt de l'application
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

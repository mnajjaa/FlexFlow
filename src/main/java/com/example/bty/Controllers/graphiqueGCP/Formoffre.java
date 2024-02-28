package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Optional;

public class Formoffre extends Application {

    private TextField idField;
    private ChoiceBox<String> specialiteChoice;
    private TextField tarifField;
    private TextField coachField;

    private int id = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formulaire d'ajout d'une offre ");
        // Card Container
        VBox cardContainer = new VBox();
        cardContainer.getStyleClass().add("card-container");
        cardContainer.setPadding(new Insets(20));
        cardContainer.setSpacing(10);

        // Card Title
        Label cardTitle = new Label("Ajouter une demande ");
        cardTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #dac8ee; -fx-font-family: 'Arial', sans-serif;");

        // Form Grid
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setStyle("-fx-background-color: #e1c7f1; -fx-padding: 25; -fx-border-radius: 10; -fx-background-radius: 10; -fx-alignment: center;");


        // Label et champ de texte pour l'ID
        Label idLabel = new Label("ID:");
        idField = new TextField();
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);

        // Label et choix de spécialité
        Label specialiteLabel = new Label("Spécialité:");
        specialiteChoice = new ChoiceBox<>();
        specialiteChoice.getItems().addAll("Musculation", "Cardio", "Yoga", "Boxe");
        specialiteChoice.setValue("Musculation"); // Spécialité par défaut
        grid.add(specialiteLabel, 0, 1);
        grid.add(specialiteChoice, 1, 1);


        // Label et champ de texte pour le tarif par heure
        Label tarifLabel = new Label("Tarif par heure:");
        tarifField = new TextField();
        grid.add(tarifLabel, 0, 2);
        grid.add(tarifField, 1, 2);

        // Label et champ de texte pour le coach
        Label coachLabel = new Label("Coach:");
        coachField = new TextField();
        grid.add(coachLabel, 0, 3);
        grid.add(coachField, 1, 3);

        Label etatLabel = new Label("Etat:");
        grid.add(etatLabel, 0, 4);

        TextField etatField = new TextField("En Attente");
        etatField.setEditable(false);
        grid.add(etatField, 1, 4);

        // Bouton d'ajout de l'offre
        Button addButton = new Button("Ajouter");
        grid.add(addButton, 1, 5);
        addButton.setOnAction(event -> insertOffre());


        // Bouton d'ajout de l'offre
        Button updateButton = new Button("Modifier");
        grid.add(updateButton,2, 5);
        updateButton.setOnAction(event -> updateOffre());


        //
        Button deleteButton = new Button("Supprimer");
        grid.add(deleteButton, 3, 5);
        deleteButton.setOnAction(event -> deleteOffre());


        Button consulterButton = new Button("Consulter liste de demande");
        GridPane.setConstraints(consulterButton, 3, 6);
        consulterButton.setOnAction(event -> {
            // Créer une boîte de dialogue pour saisir l'ID
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Consulter liste de demande");
            dialog.setHeaderText("Veuillez entrer votre ID:");
            dialog.setContentText("ID:");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<String> result = dialog.showAndWait();

            // Vérifier si l'utilisateur a saisi une valeur
            result.ifPresent(id -> {
                if (!id.isEmpty()) {
                    consulterOffre(Integer.parseInt(id));
                } else {
                    // Gérer le cas où l'ID est vide
                    System.out.println("L'ID est vide.");
                }
            });
        });
        grid.getChildren().add(consulterButton);

        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Vérifie si la nouvelle valeur ne contient que des chiffres
                idField.setStyle("-fx-text-inner-color: red;");
            } else {
                idField.setStyle("-fx-text-inner-color: black;");
            }
        });

        tarifField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tarifField.setStyle("-fx-text-inner-color: red;");
            } else {
                tarifField.setStyle("-fx-text-inner-color: black;");
            }
        });

        coachField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                coachField.setStyle("-fx-text-inner-color: red;");
            } else {
                coachField.setStyle("-fx-text-inner-color: black;");
            }
        });



        Scene scene = new Scene(grid, 700, 650);
        scene.getStylesheets().add(getClass().getResource("/Styles/StyleAR.css").toExternalForm());
        primaryStage.setTitle("Formulaire de demande");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertOffre() {


        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Offre (id, Specialite, tarif_heure, id_Coach,etatOffre) VALUES (?, ?, ?, ?,?)";
            String etatOffre = "En Attente";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.setString(2, specialiteChoice.getValue());
            statement.setDouble(3, Double.parseDouble(tarifField.getText()));
            statement.setInt(4, Integer.parseInt(coachField.getText()));
            statement.setString(5, etatOffre);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Offre insérée avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de l'offre: " + e.getMessage());
        }
    }

    private void updateOffre() {
        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE Offre SET Specialite=?, tarif_heure=?, id_Coach=? WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, specialiteChoice.getValue());
            statement.setDouble(2, Double.parseDouble(tarifField.getText()));
            statement.setInt(3, Integer.parseInt(coachField.getText()));
            statement.setInt(4, Integer.parseInt(idField.getText()));

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Offre mise à jour avec succès !");
            } else {
                System.out.println("Aucune offre mise à jour !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'offre: " + e.getMessage());
        }
    }

    private void deleteOffre() {
        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM Offre WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Offre supprimée avec succès !");
            } else {
                System.out.println("Aucune offre supprimée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'offre: " + e.getMessage());
        }
    }

    // Méthode pour gérer l'événement du bouton "Consulter"
    private void consulterOffre(int id) {
        // Ouvrir la fenêtre de consultation des demandes avec l'ID du client
        ConsultationOffre consultationOffre = new ConsultationOffre(id);
        consultationOffre.start(new Stage());
    }






    public static void main(String[] args) {
        launch(args);
    }
}

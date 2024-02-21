package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Formoffre extends Application {

    private TextField idField;
    private ChoiceBox<String> specialiteChoice;
    private TextField tarifField;
    private TextField coachField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formulaire d'ajout d'offre");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

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

        Scene scene = new Scene(grid, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/Styles/StyleFO.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertOffre() {
        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Offre (id, Specialite, tarif_heure, id_Coach) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.setString(2, specialiteChoice.getValue());
            statement.setDouble(3, Double.parseDouble(tarifField.getText()));
            statement.setInt(4, Integer.parseInt(coachField.getText()));

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


    public static void main(String[] args) {
        launch(args);
    }
}

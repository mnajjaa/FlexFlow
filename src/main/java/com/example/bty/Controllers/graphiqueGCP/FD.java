package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FD extends Application {
    private TextField ageField;
    private TextField butField;
    private TextField niveauPhysiqueField;
    private TextField maladieChroniqueField;
    private TextField nombreHeureField;
    private TextField idUserField;
    private TextField idOffreField;

    @Override
    public void start(Stage primaryStage) {

        Image image = null;
        try {
            image = new Image(getClass().getResourceAsStream("/images/ouss13.jpg"));
        } catch (NullPointerException e) {
            throw new RuntimeException("Le fichier image n'a pas été trouvé : " + e.getMessage());
        }

        ImageView backgroundImage = new ImageView(image);
        backgroundImage.setFitHeight(650);
        backgroundImage.setFitWidth(700);

        StackPane root = new StackPane();
        root.getChildren().add(backgroundImage);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Labels
        Label ageLabel = new Label("Age:");
        GridPane.setConstraints(ageLabel, 0, 0);
        ageField = new TextField();
        GridPane.setConstraints(ageField, 1, 0);

        Label butLabel = new Label("But:");
        GridPane.setConstraints(butLabel, 0, 1);
        butField = new TextField();
        GridPane.setConstraints(butField, 1, 1);

        Label niveauPhysiqueLabel = new Label("Niveau Physique:");
        GridPane.setConstraints(niveauPhysiqueLabel, 0, 2);
        niveauPhysiqueField = new TextField();
        GridPane.setConstraints(niveauPhysiqueField, 1, 2);

        Label maladieChroniqueLabel = new Label("Maladie Chronique:");
        GridPane.setConstraints(maladieChroniqueLabel, 0, 3);
        maladieChroniqueField = new TextField();
        GridPane.setConstraints(maladieChroniqueField, 1, 3);

        Label nombreHeureLabel = new Label("Nombre d'Heures:");
        GridPane.setConstraints(nombreHeureLabel, 0, 4);
        nombreHeureField = new TextField();
        GridPane.setConstraints(nombreHeureField, 1, 4);

        Label idUserLabel = new Label("ID Utilisateur:");
        GridPane.setConstraints(idUserLabel, 0, 5);
        idUserField = new TextField();
        GridPane.setConstraints(idUserField, 1, 5);

        Label idOffreLabel = new Label("ID Offre:");
        GridPane.setConstraints(idOffreLabel, 0, 6);
        idOffreField = new TextField();
        GridPane.setConstraints(idOffreField, 1, 6);

        Label etatLabel = new Label("Etat:");
        grid.add(etatLabel, 0, 7);

        // Champ de texte pour l'état (désactivé pour l'utilisateur)
        TextField etatField = new TextField("refuser"); // Set default value to "refuser"
        etatField.setEditable(false); // Make it non-editable
        grid.add(etatField, 1, 7);

        Button sendButton = new Button("Envoyer");
        GridPane.setConstraints(sendButton, 1, 8);
        sendButton.setOnAction(event -> insertDemande());

        Button updateButton = new Button("Modifier");
        GridPane.setConstraints(updateButton, 2, 8);
        updateButton.setOnAction(event -> updateDemande());

        Button deleteButton = new Button("Supprimer");
        GridPane.setConstraints(deleteButton, 3, 8);
        deleteButton.setOnAction(event -> deleteDemande());

        grid.getChildren().addAll(ageLabel, ageField, butLabel, butField, niveauPhysiqueLabel, niveauPhysiqueField,
                maladieChroniqueLabel, maladieChroniqueField, nombreHeureLabel, nombreHeureField, idUserLabel,
                idUserField, idOffreLabel, idOffreField, sendButton,updateButton,deleteButton);

        // Ajout de la grille à la racine de la scène
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 700, 650);
        scene.getStylesheets().add(getClass().getResource("/Styles/StyleFO.css").toExternalForm());
        primaryStage.setTitle("Formulaire de demande");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertDemande() {
        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO demande (Age, But, NiveauPhysique, MaladieChronique, NombreHeure, ID_User, ID_Offre, Etat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String r="refuser";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(ageField.getText()));
            statement.setString(2, butField.getText());
            statement.setString(3, niveauPhysiqueField.getText());
            statement.setString(4, maladieChroniqueField.getText());
            statement.setInt(5, Integer.parseInt(nombreHeureField.getText()));
            statement.setInt(6, Integer.parseInt(idUserField.getText()));
            statement.setInt(7, Integer.parseInt(idOffreField.getText()));
            statement.setString(8, r);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Demande insérée avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de la demande: " + e.getMessage());
        }
    }

    private void updateDemande() {
        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE demande SET Age=?, But=?, NiveauPhysique=?, MaladieChronique=?, NombreHeure=? WHERE ID_user=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(ageField.getText()));
            statement.setString(2, butField.getText());
            statement.setString(3, niveauPhysiqueField.getText());
            statement.setString(4, maladieChroniqueField.getText());
            statement.setInt(5, Integer.parseInt(nombreHeureField.getText()));
            statement.setInt(6, Integer.parseInt(idUserField.getText()));

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Demande mise à jour avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la demande: " + e.getMessage());
        }
    }

    private void deleteDemande() {
        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM demande WHERE ID_user=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idUserField.getText()));

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Demande supprimée avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la demande: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        primaryStage.setTitle("Formulaire de demande");

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

        grid.getChildren().addAll(ageLabel, ageField, butLabel, butField, niveauPhysiqueLabel, niveauPhysiqueField,
                maladieChroniqueLabel, maladieChroniqueField, nombreHeureLabel, nombreHeureField, idUserLabel,
                idUserField, idOffreLabel, idOffreField, sendButton);

        Scene scene = new Scene(grid, 400, 300);
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

    public static void main(String[] args) {
        launch(args);
    }
}

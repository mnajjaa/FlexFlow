package com.example.bty.Controllers.graphiqueGCP;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.example.bty.Entities.Role;
import com.example.bty.Utils.Session;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Entities.User;

import java.sql.*;
import java.util.Optional;

public class Formoffre extends Application {

    private TextField nomField;
    private ChoiceBox<String> specialiteChoice;
    private TextField tarifField;
    private TextField coachField;
    private TextField emailField;
    private Label nomLabel;
    Session session = Session.getInstance();
    User u=session.getLoggedInUser();
    User user ;


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


        Label nomLabel = new Label("Nom d'offre:");
        nomField = new TextField();
        grid.add(nomLabel, 0, 0);
        grid.add(nomField, 1, 0);

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
        Label coachLabel = new Label("id_Coach:");
        coachField = new TextField();
        grid.add(coachLabel, 0, 3);
        grid.add(coachField, 1, 3);

        Label etatLabel = new Label("Etat:");
        grid.add(etatLabel, 0, 4);

        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        grid.add(nomLabel, 0, 5);
        grid.add(emailField, 1, 5);

        TextField etatField = new TextField("En Attente");
        etatField.setEditable(false);
        grid.add(etatField, 1, 4);

        // Bouton d'ajout de l'offre
        Button addButton = new Button("Ajouter");
        grid.add(addButton, 1, 5);
        addButton.setOnAction(event -> insertOffre());


        Button consulterButton = new Button("Consulter liste des offres");
        grid.add(consulterButton, 2, 5);
        consulterButton.setOnAction(event -> consulterOffres());




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

        setupStringValidation(nomField);

        Scene scene = new Scene(grid, 700, 650);
        scene.getStylesheets().add(getClass().getResource("/Styles/StyleAR.css").toExternalForm());
        primaryStage.setTitle("Formulaire de demande");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void insertOffre() {
        this.user = session.getLoggedInUser();

        if (user == null) {
            showAlert(AlertType.ERROR, "Utilisateur non connecté !");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if(user.getRole().equals(Role.COACH)){
                showAlert(AlertType.INFORMATION, "Coach connecté !");
                // Le reste du code...
            } else {
                showAlert(AlertType.WARNING, "Vous n'êtes pas autorisé, vous n'êtes pas un coach !");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erreur lors de l'insertion de l'offre: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private boolean offreExists(Connection conn, String nom) throws SQLException {
        String query = "SELECT COUNT(*) FROM Offre WHERE nom = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, nom);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    }
    private void setupStringValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty() && newValue.matches(".*\\d.*")) {
                // Vérifie si la nouvelle valeur n'est pas vide et contient des chiffres
                textField.setStyle("-fx-text-inner-color: red;"); // Change la couleur du texte en rouge
            } else {
                textField.setStyle("-fx-text-inner-color: black;"); // Remet la couleur du texte en noir si aucun chiffre n'est présent
            }
        });
    }

    private void consulterOffres() {
        ConsultationOffre consultationOffre = new ConsultationOffre();
        consultationOffre.start(new Stage());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.bty.Controllers.CourController;

import com.example.bty.Utils.ConnexionDB;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
//import com.example.pidevgraphique.Cours;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.bty.Entities.Cours;
public class CourMembre extends Application {

    private static Connection connection;
    private BorderPane root;
    private Label messageLabel;

    public CourMembre() {
        connection = ConnexionDB.getInstance().getConnexion();
    }
    private ObservableList<Cours> coursList;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Affichage des cours");

        root = new BorderPane(); // Initialisation de root

        root.setPadding(new Insets(10));

        VBox filterBox = new VBox();
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setStyle("-fx-background-color: #2c3e50;\n" +
                "    -fx-padding: 15;\n" +
                "    -fx-border-radius: 15px;");
        filterBox.setSpacing(5);

        ComboBox<String> cibleComboBox = new ComboBox<>();
        cibleComboBox.getItems().addAll("Enfant", "Adulte");

        ComboBox<String> categorieComboBox = new ComboBox<>();
        categorieComboBox.getItems().addAll("Aquatique", "Cardio", "Force", "Danse", "Kids Island");

        ComboBox<String> objectifComboBox = new ComboBox<>();
        objectifComboBox.getItems().addAll("Perdre du poids", "Se défouler", "Se musculer", "S'entrainer en dansant");

        Button filterButton = new Button("Filtrer");
        filterButton.getStyleClass().add("envoyer-button");
        filterButton.setOnAction(e -> {
            String categorie = categorieComboBox.getValue();
            String cible = cibleComboBox.getValue();
            String objectif = objectifComboBox.getValue();
            filtrerCours(categorie, cible, objectif);
        });

        Button resetButton = new Button("Réinitialiser");
        resetButton.getStyleClass().add("reset-button");
        resetButton.setOnAction(e -> {
            cibleComboBox.getSelectionModel().clearSelection();
            categorieComboBox.getSelectionModel().clearSelection();
            objectifComboBox.getSelectionModel().clearSelection();
            afficherCours(); // Réafficher tous les cours
        });

        HBox filterHBox = new HBox(cibleComboBox, categorieComboBox, objectifComboBox, filterButton, resetButton);
        filterHBox.setAlignment(Pos.CENTER_LEFT);
        filterHBox.setSpacing(10);

        filterBox.getChildren().addAll(filterHBox);
        root.setTop(filterBox);

        coursList = FXCollections.observableArrayList();
        afficherCours();

        // Initialisation de messageLabel
        messageLabel = new Label();
        root.setBottom(messageLabel); // Ajout de messageLabel au bas de root

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        String cssFile = getClass().getResource("/com/example/bty/CSSmoduleCours/membre.css").toExternalForm();
        scene.getStylesheets().add(cssFile);

        primaryStage.setTitle("Consultation des cours");
        primaryStage.show();
    }

    private VBox createCourseCard(Cours cours) {
        VBox card = new VBox();
        card.getStyleClass().add("product-card");
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(6);
        card.setPrefWidth(200);
        card.setMinHeight(330);

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(cours.getImage())));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Label nomLabel = new Label(cours.getNom());
        nomLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;-fx-text-fill: #A62609");

        card.getChildren().addAll(imageView, nomLabel);

        Button participerButton = new Button("Participer");
        participerButton.getStyleClass().add("add-to-cart-button");

        participerButton.setOnAction(e -> {
            System.out.println("Mabrouk");
        });

        HBox participerHBox = new HBox(participerButton);
        participerHBox.setAlignment(Pos.CENTER);
        participerHBox.setSpacing(10);
        card.getChildren().add(participerHBox);

        return card;
    }

    private void afficherCours() {
        coursList.clear();
        try {
            String query = "SELECT * FROM cours";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_cour");
                String nom = resultSet.getString("nomCour");
                String duree = resultSet.getString("Duree");
                String intensite = resultSet.getString("Intensite");
                String cible = resultSet.getString("Cible");
                String categorie = resultSet.getString("Categorie");
                String objectif = resultSet.getString("Objectif");
                boolean etat = resultSet.getBoolean("etat");
                int capacite = resultSet.getInt("Capacite");
                byte[] imageBytes = resultSet.getBytes("image");

                Cours cours = new Cours();
                cours.setId(id);
                cours.setNom(nom);
                cours.setDuree(duree);
                cours.setIntensite(intensite);
                cours.setCible(cible);
                cours.setCategorie(categorie);
                cours.setObjectif(objectif);
                cours.setEtat(etat);
                cours.setCapacite(capacite);
                cours.setImage(imageBytes);

                coursList.add(cours);
            }

            afficherCoursDansGridPane(); // Ajouter les cours au GridPane
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filtrerCours(String categorie, String cible, String objectif) {
        coursList.clear();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM cours WHERE ");
            boolean hasFilter = false;

            if (categorie != null && !categorie.isEmpty()) {
                queryBuilder.append("Categorie = ? ");
                hasFilter = true;
            }

            if (cible != null && !cible.isEmpty()) {
                if (hasFilter) {
                    queryBuilder.append("AND ");
                }
                queryBuilder.append("Cible = ? ");
                hasFilter = true;
            }

            if (objectif != null && !objectif.isEmpty()) {
                if (hasFilter) {
                    queryBuilder.append("AND ");
                }
                queryBuilder.append("Objectif = ? ");
                hasFilter = true;
            }

            if (!hasFilter) {
                afficherAucunProduitMessage();
                return;
            }

            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());
            int parameterIndex = 1;
            if (categorie != null && !categorie.isEmpty()) {
                statement.setString(parameterIndex++, categorie);
            }
            if (cible != null && !cible.isEmpty()) {
                statement.setString(parameterIndex++, cible);
            }
            if (objectif != null && !objectif.isEmpty()) {
                statement.setString(parameterIndex, objectif);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_cour");
                String nom = resultSet.getString("nomCour");
                String duree = resultSet.getString("Duree");
                String intensite = resultSet.getString("Intensite");
                String cibleResult = resultSet.getString("Cible");
                String categorieResult = resultSet.getString("Categorie");
                String objectifResult = resultSet.getString("Objectif");
                boolean etat = resultSet.getBoolean("etat");
                int capacite = resultSet.getInt("Capacite");
                byte[] imageBytes = resultSet.getBytes("image");

                Cours cours = new Cours();
                cours.setId(id);
                cours.setNom(nom);
                cours.setDuree(duree);
                cours.setIntensite(intensite);
                cours.setCible(cibleResult);
                cours.setCategorie(categorieResult);
                cours.setObjectif(objectifResult);
                cours.setEtat(etat);
                cours.setCapacite(capacite);
                cours.setImage(imageBytes);

                coursList.add(cours);
            }

            if (coursList.isEmpty()) {
                afficherAucunProduitMessage();
            } else {
                afficherCoursDansGridPane();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher un message dans le label
    private void afficherMessage(String message) {
        messageLabel.setText(message);
    }

    // Méthode pour effacer le message du label
    private void effacerMessage() {
        messageLabel.setText("");
    }

    // Modifier la méthode afficherAucunProduitMessage pour afficher le message dans l'interface utilisateur
    private void afficherAucunProduitMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Aucun produit ne correspond à votre sélection.");
        alert.showAndWait();
    }

    private void afficherCoursDansGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        int columnIndex = 0;
        int rowIndex = 0;
        for (Cours cours : coursList) {
            VBox card = createCourseCard(cours);
            gridPane.add(card, columnIndex, rowIndex);
            columnIndex++;
            if (columnIndex == 3) {
                columnIndex = 0;
                rowIndex++;
            }
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        root.setCenter(scrollPane); // Ajout du scrollPane au centre de root
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.bty.Controllers.CourController;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Utils.ConnexionDB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.shape.Line;
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

import static com.example.bty.Services.ServiceCours.filtrerCours;
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
                "    -fx-border-radius: 15px;" +
                "-fx-background-insets: 0 0 0 219");

        filterBox.setSpacing(5);

        ComboBox<String> cibleComboBox = new ComboBox<>();
        cibleComboBox.getItems().addAll("Enfant", "Adulte");
        cibleComboBox.setPromptText("Cible");


        ComboBox<String> categorieComboBox = new ComboBox<>();
        categorieComboBox.getItems().addAll("Aquatique", "Cardio", "Force", "Danse", "Kids Island");
        categorieComboBox.setPromptText("Catégorie");


        ComboBox<String> objectifComboBox = new ComboBox<>();
        objectifComboBox.getItems().addAll("Perdre du poids", "Se défouler", "Se musculer", "S'entrainer en dansant");
        objectifComboBox.setPromptText("Objectif");


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
            // Réinitialiser le texte d'invite pour chaque ComboBox
            cibleComboBox.setPromptText("Cible");
            categorieComboBox.setPromptText("Catégorie");
            objectifComboBox.setPromptText("Objectif");

            // Vider la sélection dans chaque ComboBox
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

        // Ajoutez le contenu principal dans un ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Ajoutez le contenu principal (grille de cours) dans le ScrollPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        // Initialisation de messageLabel
        messageLabel = new Label();
        root.setBottom(messageLabel); // Ajout de messageLabel au bas de root

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        String cssFile = getClass().getResource("/com/example/bty/CSSmoduleCours/membre.css").toExternalForm();
        scene.getStylesheets().add(getClass().getResource("/dashboardDesign.css").toExternalForm());
        scene.getStylesheets().add(cssFile);

        primaryStage.setTitle("Consultation des cours");
        primaryStage.show();

        AnchorPane leftDashboard = createLeftDashboard();
        leftDashboard.setMinHeight(250);
        root.setLeft(leftDashboard);

        root.setLeft(leftDashboard);
    }


    private AnchorPane createLeftDashboard() {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(1100, 600);

        AnchorPane anchorPane = new AnchorPane();
        stackPane.getChildren().add(anchorPane);


        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1100, 600);
        borderPane.getStyleClass().add("border-pane");

        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);

        AnchorPane leftAnchorPane = new AnchorPane();
        //leftAnchorPane.setTranslateY(-80);
        leftAnchorPane.setTranslateX(-10);
        // leftAnchorPane.setPrefSize(270, 700);
        AnchorPane.setTopAnchor(leftAnchorPane, 0.0);
        AnchorPane.setLeftAnchor(leftAnchorPane, 0.0);
        leftAnchorPane.setTranslateY(-70);
        AnchorPane innerAnchorPane = new AnchorPane();
        innerAnchorPane.getStyleClass().addAll("nav", "nav-border");
        innerAnchorPane.setPrefSize(229, 900);

        FontAwesomeIconView userIcon = new FontAwesomeIconView();
        userIcon.setFill(javafx.scene.paint.Color.WHITE);
        userIcon.setGlyphName("USER");
        userIcon.setLayoutX(82);
        userIcon.setLayoutY(91);
        userIcon.setSize("6em");

        Label welcomeLabel = new Label("Welcome,");
        welcomeLabel.setLayoutX(78);
        welcomeLabel.setLayoutY(101);
        welcomeLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        welcomeLabel.setFont(new javafx.scene.text.Font("Tahoma", 15));

        Label usernameLabel = new Label("MarcoMan");
        usernameLabel.setId("username");
        usernameLabel.setAlignment(javafx.geometry.Pos.CENTER);
        usernameLabel.setLayoutX(11);
        usernameLabel.setLayoutY(120);
        usernameLabel.setPrefSize(201, 23);
        usernameLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        usernameLabel.setFont(new javafx.scene.text.Font("Arial Bold", 20));

        Line line = new Line();
        line.setEndX(100);
        line.setLayoutX(111);
        line.setLayoutY(152);
        line.setStartX(-100);
        line.setStroke(javafx.scene.paint.Color.WHITE);


        Button dashboardBtn = new Button("Dashboard");
        dashboardBtn.setId("dashboard_btn");
        dashboardBtn.setLayoutX(21);
        dashboardBtn.setLayoutY(170);
        dashboardBtn.setMnemonicParsing(false);
        dashboardBtn.setPrefSize(180, 35);
        dashboardBtn.getStyleClass().addAll("nav-btn", "dashboard-btn");
        dashboardBtn.setText("Dashboard");

        Button userBtn = new Button("Utilisateurs");
        userBtn.setId("user_btn");
        userBtn.setLayoutX(21);
        userBtn.setLayoutY(220);
        userBtn.setMnemonicParsing(false);
        userBtn.setPrefSize(180, 35);
        userBtn.getStyleClass().addAll("nav-btn");

        Button coursBtn = new Button("Cours");
        coursBtn.setId("cours_btn");
        coursBtn.setLayoutX(21);
        coursBtn.setLayoutY(270);
        coursBtn.setMnemonicParsing(false);
        coursBtn.setPrefSize(180, 35);
        coursBtn.getStyleClass().addAll("nav-btn");

        Button evenementBtn = new Button("Evenement");
        evenementBtn.setId("evenement_btn");
        evenementBtn.setLayoutX(21);
        evenementBtn.setLayoutY(320);
        evenementBtn.setMnemonicParsing(false);
        evenementBtn.setPrefSize(180, 35);
        evenementBtn.getStyleClass().addAll("nav-btn");

        Button produitBtn = new Button("Produits");
        produitBtn.setId("produit_btn");
        produitBtn.setLayoutX(21);
        produitBtn.setLayoutY(370);
        produitBtn.setMnemonicParsing(false);
        produitBtn.setPrefSize(180, 35);
        produitBtn.getStyleClass().addAll("nav-btn");

        Button coachBtn = new Button("Coaching privé");
        coachBtn.setId("coach_btn");
        coachBtn.setLayoutX(21);
        coachBtn.setLayoutY(420);
        coachBtn.setMnemonicParsing(false);
        coachBtn.setPrefSize(180, 35);
        coachBtn.getStyleClass().addAll("nav-btn");

        Button reclamationBtn = new Button("Réclamation");
        reclamationBtn.setId("reclamation_btn");
        reclamationBtn.setLayoutX(21);
        reclamationBtn.setLayoutY(470);
        reclamationBtn.setMnemonicParsing(false);
        reclamationBtn.setPrefSize(180, 35);
        reclamationBtn.getStyleClass().addAll("nav-btn");

        Button logoutBtn = new Button();
        logoutBtn.setId("logout");
        logoutBtn.setLayoutX(14);
        logoutBtn.setLayoutY(545);
        logoutBtn.setMnemonicParsing(false);
        logoutBtn.getStyleClass().addAll("logout", "shadow");


        FontAwesomeIconView logoutIcon = new FontAwesomeIconView();
        logoutIcon.setFill(javafx.scene.paint.Color.WHITE);
        logoutIcon.setGlyphName("SIGN_OUT");
        logoutIcon.setSize("2em");

        logoutBtn.setGraphic(logoutIcon);

        Label logoutLabel = new Label("Logout");
        logoutLabel.setLayoutX(58);
        logoutLabel.setLayoutY(551);
        logoutLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        logoutLabel.setFont(new javafx.scene.text.Font(15));

        innerAnchorPane.getChildren().addAll(userIcon, welcomeLabel, usernameLabel, line, dashboardBtn, userBtn, coursBtn, evenementBtn, produitBtn, coachBtn, reclamationBtn, logoutBtn, logoutLabel);

        leftAnchorPane.getChildren().add(innerAnchorPane);

        borderPane.setLeft(leftAnchorPane);
        anchorPane.getChildren().add(borderPane);
        return leftAnchorPane;
    }


    private VBox createCourseCard(Cours cours) {
        VBox card = new VBox();
        card.getStyleClass().add("product-card");
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(6);
        card.setPrefWidth(200);
        card.setMinHeight(330);

        Image image = null;
        byte[] imageData = cours.getImage();
        if (imageData != null && imageData.length > 0) {
            image = new Image(new ByteArrayInputStream(imageData));
        }

        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            card.getChildren().add(imageView);
        }

        Label nomLabel = new Label(cours.getNom());
        nomLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;-fx-text-fill: #A62609");
        card.getChildren().add(nomLabel);

        Button participerButton = new Button("Participer");
        participerButton.getStyleClass().add("add-to-cart-button");

        // Vérifier la capacité restante avant de permettre la participation
        participerButton.setOnAction(e -> participerAuCours(cours, participerButton));

        HBox participerHBox = new HBox(participerButton);
        participerHBox.setAlignment(Pos.CENTER);
        participerHBox.setSpacing(10);
        card.getChildren().add(participerHBox);

        return card;
    }

    private void participerAuCours(Cours cours, Button participerButton) {
        try {
            String username = "dridi"; // Remplacez par le nom d'utilisateur de l'utilisateur connecté
            User utilisateurConnecte = getUtilisateurConnecte(username);

            if (utilisateurConnecte != null) {
                if (cours.getCapacite() > 0) {
                    // Enregistrez la participation dans la base de données
                    String queryInsert = "INSERT INTO participation (id_user, nomCour) VALUES (?, ?)";
                    PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
                    statementInsert.setInt(1, utilisateurConnecte.getId());
                    statementInsert.setString(2, cours.getNom());
                    statementInsert.executeUpdate();

                    System.out.println("Vous avez participé au cours: " + cours.getNom());
                    cours.setCapacite(cours.getCapacite() - 1); // Diminuer la capacité du cours

                    // Mettre à jour la capacité dans la table cours
                    String queryUpdate = "UPDATE cours SET capacite = ? WHERE nomCour = ?";
                    PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate);
                    statementUpdate.setInt(1, cours.getCapacite());
                    statementUpdate.setString(2, cours.getNom());
                    statementUpdate.executeUpdate();

                    if (cours.getCapacite() == 0) {
                        // Désactiver le bouton participer si la capacité est épuisée
                        participerButton.setDisable(true);
                        participerButton.setText("Complet");
                    }
                } else {
                    System.out.println("La capacité de ce cours est épuisée. Vous ne pouvez plus participer.");
                }
            } else {
                System.out.println("Veuillez vous connecter pour participer au cours.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la participation au cours.");
        }
    }

    private User getUtilisateurConnecte(String username) {
        User utilisateurConnecte = null;
        try {
            String query = "SELECT * FROM user WHERE nom = ? AND role = 'MEMBRE'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String telephone = resultSet.getString("telephone");
                Role role = Role.valueOf(resultSet.getString("role"));

                utilisateurConnecte = new User(id, nom, email, password, telephone, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurConnecte;
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
            if (columnIndex == 5) {
                columnIndex = 0;
                rowIndex++;
            }
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        root.setCenter(scrollPane); // Ajout du scrollPane au centre de root
    }

    private void afficherCours() {
        coursList.clear();
        try {
            String query = "SELECT * FROM cours WHERE etat = 1 ";
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
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM cours WHERE etat = 1 "); // Ajoutez cette condition pour filtrer les cours avec un état de 1

            boolean hasFilter = false;

            if (categorie != null && !categorie.isEmpty()) {
                queryBuilder.append("AND Categorie = ? ");
                hasFilter = true;
            }

            if (cible != null && !cible.isEmpty()) {
                queryBuilder.append("AND Cible = ? ");
                hasFilter = true;
            }

            if (objectif != null && !objectif.isEmpty()) {
                queryBuilder.append("AND Objectif = ? ");
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


    private void afficherAucunProduitMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Aucun produit ne correspond à votre sélection.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
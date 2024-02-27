package com.example.bty.Controllers.EvenementController;

import com.example.bty.Entities.Evenement;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Popup;
import javafx.stage.Stage;


import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class clientVitrine extends Application {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pidevgym";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    private Map<Evenement, VBox> detailsMap = new HashMap<>();

    private Map<Integer, Boolean> popupOpenMap = new HashMap<>(); // Track open popups for each event


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vitrine Client");

        // Récupérer la liste des événements (vous devez implémenter cette méthode)
        List<Evenement> evenements = getListeEvenements();

        // Créer une VBox pour afficher les cartes d'événements
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        BorderPane root = new BorderPane();
        VBox topBar = new VBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");
        topBar.setStyle(" -fx-background-color: #2c3e50;\n" +
                "    -fx-padding: 25;\n" +
                "    -fx-border-radius: 15px;" +
                " -fx-background-insets: 0 0 0 218;");
        topBar.setSpacing(5);

        root.setTop(topBar);



        AnchorPane leftDashboard = createLeftDashboard();

        root.setLeft(leftDashboard);





        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setPadding(new Insets(20));

        flowPane.setHgap(30);
        flowPane.setVgap(30);







        scrollPane.setContent(flowPane);
        root.setCenter(scrollPane);








        Scene scene = new Scene(root, 800, 600);
        primaryStage.setResizable(true);

        primaryStage.setScene(scene);

        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/CSSmoduleEvenement/VitrineClient.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/dashboardDesign.css").toExternalForm());
// Parcourir la liste des événements et créer une carte pour chaque événement
        for (Evenement evenement : evenements) {

            VBox carte = createEventCard(evenement);
            flowPane.getChildren().add(carte);
        }
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
        //  leftAnchorPane.setTranslateY(-80);
        // leftAnchorPane.setTranslateX(-10);
        leftAnchorPane.setPrefSize(270, 600);
        //leftAnchorPane.setPrefHeight(Double.MAX_VALUE);




        AnchorPane.setTopAnchor(leftAnchorPane, 0.0);
        AnchorPane.setLeftAnchor(leftAnchorPane, 0.0);
        //leftAnchorPane.setTranslateY(-112);
        AnchorPane innerAnchorPane = new AnchorPane();
        innerAnchorPane.getStyleClass().addAll("nav", "nav-border");
        innerAnchorPane.setPrefSize(229, 750);

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
        leftAnchorPane.setPrefHeight(Double.MAX_VALUE);

        borderPane.setLeft(leftAnchorPane);
        anchorPane.getChildren().add(borderPane);
        return leftAnchorPane;
    }

    private VBox createEventCard(Evenement evenement) {
//        VBox carte = new VBox(10);
//        carte.setPadding(new Insets(10));
        VBox card = new VBox();
        card.getStyleClass().add("product-card");
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(10);
        card.setPrefWidth(220); // Définir une largeur préférée fixe
        card.setMinHeight(250);

        // Afficher l'image de l'événement
        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(evenement.getImage())));

        imageView.setFitWidth(200);
        imageView.setFitHeight(170);

        // Afficher le nom de l'événement
        Label nomLabel = new Label( evenement.getNom());
        nomLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;-fx-text-fill: #A62609;");




        // Ajouter un bouton "See More"
        Button SeeButton = new Button("See more");
        SeeButton.getStyleClass().add("add-to-cart-button");


        boolean[] isPopupShowing = {false}; // Using an array to make it effectively final

        SeeButton.setOnAction(e -> {
            // Retrieve or create the details card for the specific event
            VBox detailsCard = detailsMap.computeIfAbsent(evenement, k -> DetailsEvenementWindow.createDetailsCard(k));

            // Create a new Popup
            Popup popup = new Popup();
            popup.getContent().add(detailsCard);

            // Set the position of the Popup relative to the button
            popup.setOnShowing(ev -> {
                Bounds bounds = SeeButton.localToScreen(SeeButton.getBoundsInLocal());
                popup.setX(bounds.getMaxX());
                popup.setY(bounds.getMinY());
            });


            // Close the Popup on double-click
            if (isPopupShowing[0]) {
                popup.hide();
            } else {
                popup.show(SeeButton.getScene().getWindow());
            }

            // Update the visibility flag
            isPopupShowing[0] = !isPopupShowing[0];
        });



        // Ajouter les éléments à la carte
        card.getChildren().addAll(imageView, nomLabel, SeeButton);

        return card;
    }


    // Méthode pour afficher plus de détails sur l'événement (vous devez implémenter cette méthode)
    private void SeeMoreEvenement(Evenement evenement) {
        // Implémentez le code pour afficher plus de détails sur l'événement
        // Vous pouvez utiliser une nouvelle fenêtre ou un panneau pour afficher les détails
      DetailsEvenementWindow.createDetailsCard(evenement);

    }

    private List<Evenement> getListeEvenements() {
        List<Evenement> evenements = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM evenement WHERE etat=1 ";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id_evenement");
                    String nom = resultSet.getString("nomEvenement");
                    byte[] imageBytes = resultSet.getBytes("image");
                    String categorie = resultSet.getString("categorie");
                    String Objectif =resultSet.getString("Objectif");
                    Date date =resultSet.getDate("Date");
                    Time time=resultSet.getTime("Time");
                    int nbrPlace = resultSet.getInt("nbrPlace");


                    try {
                        Evenement evenement = new Evenement(id, nom, imageBytes,categorie,Objectif,date,time,nbrPlace);
                        evenements.add(evenement);
                    } catch (Exception e) {
                        System.err.println("Erreur lors de la conversion de l'image pour l'événement avec l'ID : " + id);
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evenements;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
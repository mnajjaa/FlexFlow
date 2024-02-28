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
import javafx.scene.text.Text;
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
                " -fx-background-insets: 0 0 0 232;"

        );
       // topBar.setSpacing(5);

        root.setTop(topBar);



        AnchorPane leftDashboard = createLeftDashboard(primaryStage);

        root.setLeft(leftDashboard);





        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setPadding(new Insets(20));

        flowPane.setHgap(30);
        flowPane.setVgap(30);







        scrollPane.setContent(flowPane);
        root.setCenter(scrollPane);











        Scene scene = new Scene(root, 1536, 780);
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
    private AnchorPane createLeftDashboard(Stage primaryStage) {
        AnchorPane mainForm = new AnchorPane();
        mainForm.setPrefSize(1100, 900);

        //AnchorPane dashboardAdmin = new AnchorPane();



        AnchorPane dashboardAdmin = new AnchorPane();
        //leftAnchorPane.setTranslateY(-80);
        dashboardAdmin.setTranslateX(0);
        //leftAnchorPane.setPrefSize(70, 280);
//        dashboardAdmin.setBottomAnchor(mainForm, 40.0);
//        dashboardAdmin.setLeftAnchor(mainForm, 40.0);
        dashboardAdmin.setTranslateY(-90);
        dashboardAdmin.setPrefSize(234, 1600);
        dashboardAdmin.getStyleClass().add("border-pane");


        FontAwesomeIconView usernameAdmin = createFontAwesomeIconView("USER", "WHITE", 50, 82, 91);
        Label welcomeLabel = createLabel("Welcome,", "Tahoma", 15, 78, 101,"WHITE");
        Label usernameLabel = createLabel("MarcoMan", "Arial Bold", 20, 11, 120,"WHITE");
        // Line line = createLine(-100, 152, 100, 152, 111);
        Line line = createColoredLine(-100, 152, 100, 152, 111, "WHITE");

        Button DashboardBtn = createButton("Dashboard", 22, 186);
        Button CoursBtn = createButton("Cours", 22, 234);
        Button eventsBtn = createButton("Evenements", 22, 276);
        Button demandeBtn = createButton("Demande Coahing", 22, 319);
        Button offreAdminBtn = createButton("Offre", 22, 361);
        Button storeAdminBtn = createButton("Store", 22, 405);

//        CoursBtn.setOnAction(event -> {
//            // Instancier et afficher la vue DashboardVitrineController
//            CourMembre v = new CourMembre();
//            v.start(primaryStage);
//        });


        eventsBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController
            clientVitrine v=new clientVitrine();
            v.start(primaryStage);
        });


      /*  demandeBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController
            FD f = new FD();
            f.start(primaryStage);
        });*/


        offreAdminBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });


        storeAdminBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });



        Line line2 = createColoredLine(-100, 449, 100, 449, 112, "WHITE");

        Button profileAdminBtn = createButton("Profile", 22, 462);
        Button logoutBtn = createButton("Logout", 22, 503);

        FontAwesomeIconView[] icons = {
                createFontAwesomeIconView("HOME", "WHITE", 20, 38, 212),
                createFontAwesomeIconView("USER", "WHITE", 20, 38, 258),
                createFontAwesomeIconView("USERS", "WHITE", 20, 38, 300),
                createFontAwesomeIconView("BOOK", "WHITE", 20, 38, 343),
                createFontAwesomeIconView("CALENDAR", "WHITE", 20, 38, 385),

                createFontAwesomeIconView("SHOPPING_CART", "WHITE", 20, 38, 429),

                createFontAwesomeIconView("ID_CARD", "WHITE", 20, 38, 486),
                createFontAwesomeIconView("EXTERNAL_LINK", "WHITE", 20, 38, 529)
        };

        VBox reportContainer = new VBox();
        reportContainer.setLayoutX(13);
        reportContainer.setLayoutY(750);
        reportContainer.setPrefSize(180, 91);
        reportContainer.setStyle(" -fx-background-color:WHITE; /* Bleu */\n" +
                "    -fx-border-radius: 15;\n" +
                "    -fx-background-radius:15;\n" +
                "    -fx-border-color:#2c3e50; /* Couleur de bordure bleue légère */\n" +
                "    -fx-padding: 12;\n" +
                "    -fx-translate-y: -30;");

        Text reportText = new Text("Report Suggestion/Bug?");
        reportText.getStyleClass().add("report_text");

        Label reportLabel = new Label("Use this to report any errors or suggestions.");
        reportLabel.getStyleClass().add("report_label");

        Button reportButton = createButton("Report", 0, 0);
        reportButton.getStyleClass().add("report_button");



        reportContainer.getChildren().addAll(reportText, reportLabel, reportButton);

        StackPane contentPlaceholder = new StackPane();
        contentPlaceholder.setLayoutX(220);
        contentPlaceholder.setLayoutY(0);

        dashboardAdmin.getChildren().addAll(
                usernameAdmin, welcomeLabel, usernameLabel, line,
                DashboardBtn,CoursBtn, eventsBtn, demandeBtn, offreAdminBtn,
                storeAdminBtn, line2, profileAdminBtn,
                logoutBtn, icons[0], icons[1], icons[2], icons[3],
                icons[4], icons[5], icons[6],icons[7], reportContainer,
                contentPlaceholder
        );

        mainForm.getChildren().addAll(dashboardAdmin);
        return dashboardAdmin;


    }



    private FontAwesomeIconView createFontAwesomeIconView(String glyphName, String fill, double size, double layoutX, double layoutY) {
        FontAwesomeIconView iconView = new FontAwesomeIconView();
        iconView.setGlyphName(glyphName);

        // Définir la couleur de remplissage ici
        iconView.setFill(javafx.scene.paint.Paint.valueOf(fill));

        iconView.setSize(String.valueOf(size));

        iconView.setLayoutX(layoutX);
        iconView.setLayoutY(layoutY);
        return iconView;
    }


    private Label createLabel(String text, String fontName, double fontSize, double layoutX, double layoutY, String textFill) {
        Label label = new Label(text);
        label.setFont(new javafx.scene.text.Font(fontName, fontSize));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);

        // Définir la couleur du texte ici
        label.setTextFill(javafx.scene.paint.Paint.valueOf(textFill));

        return label;
    }


    private Line createLine(double startX, double startY, double endX, double endY, double layoutX) {
        Line line = new Line(startX, startY, endX, endY);
        line.setLayoutX(layoutX);
        return line;
    }


    private Line createColoredLine(double startX, double startY, double endX, double endY, double layoutX, String strokeColor) {
        Line line = new Line(startX, startY, endX, endY);
        line.setLayoutX(layoutX);

        // Définir la couleur de la ligne ici
        line.setStroke(javafx.scene.paint.Paint.valueOf(strokeColor));

        return line;
    }

    private Button createButton(String text, double layoutX, double layoutY) {
        Button button = new Button(text);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setMnemonicParsing(false);
        button.getStyleClass().add("nav-btn");
        button.setPrefSize(180, 35);
        return button;
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
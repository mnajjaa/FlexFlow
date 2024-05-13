package com.example.bty.Controllers.EvenementController;

import com.example.bty.Utils.ConnexionDB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DashboardMembre extends Application {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pidevgym";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    private BorderPane root;
    private DetailsEvenementWindow detailsEvenementWindow;

    public DashboardMembre(){
        connection = ConnexionDB.getInstance().getConnexion();
    }
    public void start(Stage primaryStage) throws Exception {

        root = new BorderPane();
        AnchorPane leftDashboard = createLeftDashboard(primaryStage);
        BorderPane root = new BorderPane();
        root.setLeft(leftDashboard);
        detailsEvenementWindow = new DetailsEvenementWindow();
        BarChart<String, Number> eventDateBarChart = createEventDateBarChartForClient();
        //root.setCenter(eventDateBarChart);
// Créer un SplitPane pour diviser la fenêtre horizontalement
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(eventDateBarChart, new AnchorPane()); // Ajouter le graphique et un panneau vide
        splitPane.setDividerPositions(0.5f); // Positionner le diviseur à mi-chemin



        root.setCenter(splitPane);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard membre");
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/dashboardDesign.css").toExternalForm());

    }

    public BarChart<String, Number> createEventDateBarChartForClient() {
        ObservableList<XYChart.Series<String, Number>> seriesList = FXCollections.observableArrayList();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> eventDateBarChart = new BarChart<>(xAxis, yAxis);

        try {
            String query = "SELECT nomEvenement, Date FROM evenement";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            Map<String, XYChart.Series<String, Number>> eventSeriesMap = new HashMap<>();
            eventDateBarChart.setMinHeight(200);  // Ajuster la hauteur minimale
            eventDateBarChart.setMaxHeight(700);  // Ajuster la hauteur maximale


            while (resultSet.next()) {
                String eventName = resultSet.getString("nomEvenement");
                java.sql.Date eventDate = resultSet.getDate("Date");
                LocalDate eventLocalDate = eventDate.toLocalDate();
                LocalDate currentDate = LocalDate.now();

                // Filtrer les événements passés
                if (eventLocalDate.isAfter(currentDate)) {
                    long daysUntilEvent = calculateDaysUntilEvent(eventDate);

                    XYChart.Series<String, Number> series = eventSeriesMap.get(eventName);
                    if (series == null) {
                        series = new XYChart.Series<>();
                        series.setName(eventName);
                        eventSeriesMap.put(eventName, series);
                        seriesList.add(series);
                    }

                    series.getData().add(new XYChart.Data<>(eventName, daysUntilEvent));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        eventDateBarChart.setData(seriesList);
        eventDateBarChart.setTitle("Durée jusqu'à la Date des Événements");
        xAxis.setLabel("Événements");
        yAxis.setLabel("Nombre du jour restant");

        return eventDateBarChart;
    }



    private long calculateDaysUntilEvent(Date eventDate) {
        // Convertir java.sql.Date en java.time.LocalDate
        LocalDate eventLocalDate = eventDate.toLocalDate();

        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();

        // Calculer la différence de jours entre la date actuelle et la date de l'événement
        return Math.abs(currentDate.until(eventLocalDate).getDays());
    }




    private AnchorPane createLeftDashboard(Stage primaryStage) {
        //User loggedInUser = Session.getInstance().getLoggedInUser();
        AnchorPane mainForm = new AnchorPane();
        mainForm.setPrefSize(1100, 900);

        //AnchorPane dashboardAdmin = new AnchorPane();



        AnchorPane dashboardAdmin = new AnchorPane();
        //leftAnchorPane.setTranslateY(-80);
        dashboardAdmin.setTranslateX(0);
        //leftAnchorPane.setPrefSize(70, 280);
//        dashboardAdmin.setBottomAnchor(mainForm, 40.0);
//        dashboardAdmin.setLeftAnchor(mainForm, 40.0);
        dashboardAdmin.setTranslateY(-5);
        dashboardAdmin.setPrefSize(234, 1500);
        dashboardAdmin.getStyleClass().add("border-pane");


        FontAwesomeIconView usernameAdmin = createFontAwesomeIconView("USER", "WHITE", 50, 82, 91);
        Label welcomeLabel = createLabel("Welcome, " , "Arial Bold", 15, 78, 101, "WHITE");
        Label usernameLabel = createLabel("MarcoMan", "Arial Bold", 20, 11, 120,"WHITE");
        // Line line = createLine(-100, 152, 100, 152, 111);
        Line line = createColoredLine(-100, 152, 100, 152, 111, "WHITE");

        Button DashboardBtn = createButton("Dashboard", 22, 186);
        Button CoursBtn = createButton("Cours", 22, 234);
        Button eventsBtn = createButton("Evenements", 22, 276);
        Button demandeBtn = createButton("Demande Coahing", 22, 319);
        Button offreAdminBtn = createButton("Offre", 22, 361);
        Button storeAdminBtn = createButton("Store", 22, 405);

        DashboardBtn.setOnAction(event->{


        });


        CoursBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });


        eventsBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });


        demandeBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });


        offreAdminBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });


        storeAdminBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });



        Line line2 = createColoredLine(-100, 449, 100, 449, 112, "WHITE");

        Button profileAdminBtn = createButton("Profile", 22, 462);
        Button logoutBtn = createButton("Logout", 22, 503);

// Add event handler to logoutBtn

// Add event handler to logoutBtn
        logoutBtn.setOnAction(event -> {
            // Close all open interfaces
            Stage primaryStage1 = (Stage) logoutBtn.getScene().getWindow(); // Assuming logoutBtn is in the same scene as the primaryStage
            primaryStage1.close();

            // Open the LoginGYM.fxml interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginGym.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        });


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
        reportContainer.setLayoutY(608);
        reportContainer.setPrefSize(180, 91);
        reportContainer.getStyleClass().add("report_container");


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

    public static void main(String[] args) {
        launch(args);
    }
}

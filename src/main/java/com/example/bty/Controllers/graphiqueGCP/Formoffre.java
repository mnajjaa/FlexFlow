package com.example.bty.Controllers.graphiqueGCP;

import com.example.bty.Controllers.CourController.CourMembre;
import com.example.bty.Controllers.ProduitController.VitrineClient;
import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Utils.Session;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Formoffre extends Application {

    private TextField nomField;
    private ChoiceBox<String> specialiteChoice;
    private TextField tarifField;
    private TextField coachField;
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


        AnchorPane sidebar = createLeftDashboard(primaryStage);

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

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        VBox formContainer = new VBox();
        formContainer.getChildren().addAll(cardTitle, grid);
        root.setCenter(formContainer);
        Scene scene = new Scene(root, 1366, 700);
        scene.getStylesheets().add(getClass().getResource("/dashboardDesign.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
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
        dashboardAdmin.setTranslateY(0);
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

        CoursBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController
            CourMembre c = new CourMembre();
            c.start(primaryStage);
        });


        eventsBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

        });


        demandeBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController
            FD f = new FD();
            f.start(primaryStage);
        });


        offreAdminBtn.setOnAction(event -> {
            Formoffre o = new Formoffre();
            o.start(primaryStage);

        });


        storeAdminBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController
            VitrineClient v = new VitrineClient();
            v.start(primaryStage);
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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
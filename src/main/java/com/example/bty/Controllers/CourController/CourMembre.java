package com.example.bty.Controllers.CourController;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.ConnexionDB;
import com.example.bty.Utils.Session;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
//import com.example.pidevgraphique.Cours;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.example.bty.Entities.Cours;

import static com.example.bty.Services.ServiceCours.filtrerCours;
public class CourMembre extends Application {

    private static Connection connection;
    private BorderPane root;
    private Label messageLabel;

    Session session = Session.getInstance();
    User u=session.getLoggedInUser();
    User user ;


    public CourMembre() {
        connection = ConnexionDB.getInstance().getConnexion();
    }

    private ObservableList<Cours> coursList;

    @Override
    public void start(Stage primaryStage)  {
     /*   Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez saisir votre email : ");
        String email = scanner.nextLine();
        System.out.print("Veuillez saisir votre mot de passe : ");
        String password = scanner.nextLine();

        IServiceUser serviceUser = new ServiceUser();
        int status = serviceUser.Authentification(email, password);
      switch (status) {
            case 0:
                System.out.println("Invalid user credentials");
                break;
            case 1:
                System.out.println("Logged in successfully");
                break;
            case 2:
                System.out.println("User is desactiver");
                break;
        }

        Session s= Session.getInstance();
        System.out.println(s.getLoggedInUser());
       // s.logout();
*/



        primaryStage.setTitle("Affichage des cours");

        root = new BorderPane(); // Initialisation de root

        // root.setPadding(new Insets(10));

        VBox filterBox = new VBox();
        filterBox.setAlignment(Pos.CENTER_LEFT);

        filterBox.setStyle("-fx-background-color: #2c3e50;\n" +
                "    -fx-padding: 15;\n" +
                "    -fx-border-radius: 15px;" +
                "-fx-background-insets: 0 0 0 219");

        //   filterBox.setSpacing(5);

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

        Scene scene = new Scene(root, 1366, 700);
        primaryStage.setScene(scene);

        String cssFile = getClass().getResource("/com/example/bty/CSSmoduleCours/membre.css").toExternalForm();
        scene.getStylesheets().add(getClass().getResource("/dashboardDesign.css").toExternalForm());
        scene.getStylesheets().add(cssFile);

        primaryStage.setTitle("Consultation des cours");
        primaryStage.show();

        AnchorPane leftDashboard = createLeftDashboard(primaryStage);
        //  leftDashboard.setMinHeight(250);
        root.setLeft(leftDashboard);

        root.setLeft(leftDashboard);
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
        dashboardAdmin.setTranslateY(-112);
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

        CoursBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController
            CourMembre v = new CourMembre();
            v.start(primaryStage);
        });


        eventsBtn.setOnAction(event -> {
            // Instancier et afficher la vue DashboardVitrineController

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
                "    -fx-translate-y: -10;");

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

  /*  private void participerAuCours(Cours cours, Button participerButton) {
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
    }*/

  /*  private User getUtilisateurConnecte(String username) {
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

                utilisateurConnecte = new User(id, nom, email, password, telephone, role );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurConnecte;
    }*/

    private void participerAuCours(Cours cours, Button participerButton) {
        // Récupérer l'utilisateur connecté à partir de la session
        User loggedInUser = Session.getInstance().getLoggedInUser();

        // Vérifier si l'utilisateur est connecté
        if (loggedInUser != null) {
            // Vérifier si l'utilisateur est un membre
            if (loggedInUser.getRole() == Role.MEMBRE) {
                // Afficher une boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de participation");
                alert.setHeaderText(null);
                alert.setContentText("Êtes-vous sûr de vouloir participer à ce cours ?");

                // Personnaliser les boutons de la boîte de dialogue
                ButtonType buttonTypeOui = new ButtonType("Oui");
                ButtonType buttonTypeNon = new ButtonType("Non");
                alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                // Attendre la réponse de l'utilisateur
                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeOui) {
                        // L'utilisateur a cliqué sur "Oui", procéder à la participation
                        participerAuCoursEffectif(cours, participerButton,  loggedInUser);
                    }
                });
            } else {
                System.out.println("Seuls les membres peuvent participer aux cours.");
            }
        } else {
            // Gérer le cas où l'utilisateur n'est pas connecté
            System.out.println("Vous devez être connecté pour participer à un cours.");
        }
    }

    private void participerAuCoursEffectif(Cours cours, Button participerButton, User loggedInUser) {
        int userID = loggedInUser.getId();
        String userName = loggedInUser.getName();

        try {
            if (cours.getCapacite() > 0) {
                // Insérer l'ID du membre et l'ID du cours dans la table de participation
                String queryInsert = "INSERT INTO participation (id_user, nomCour, nomParticipant) VALUES (?,?,?)";
                PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
                statementInsert.setInt(1, userID);
                statementInsert.setString(2, cours.getNom());
                statementInsert.setString(3, userName); // Envoyer le nom du participant à la base de données

                int rowsInserted = statementInsert.executeUpdate();

                if (rowsInserted > 0) {
                    // Participation réussie
                    System.out.println("Vous avez participé au cours avec succès !");

                    // Diminuer la capacité du cours dans la base de données
                    cours.setCapacite(cours.getCapacite() - 1);
                    String queryUpdate = "UPDATE cours SET capacite = ? WHERE id_cour = ?";
                    PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate);
                    statementUpdate.setInt(1, cours.getCapacite());
                    statementUpdate.setInt(2, cours.getId());
                    statementUpdate.executeUpdate();

                    // Vérifier si la capacité est épuisée
                    if (cours.getCapacite() == 0) {
                        participerButton.setDisable(true);
                        participerButton.setText("Complet");
                    }
                } else {
                    System.out.println("Erreur lors de la participation au cours.");
                }
            } else {
                System.out.println("La capacité de ce cours est épuisée. Vous ne pouvez plus participer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            String query = "SELECT * FROM cours WHERE etat = 1 AND capacite > 0"; // Filtrer les cours avec capacité > 0
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
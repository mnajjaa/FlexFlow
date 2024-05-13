package com.example.bty.Controllers.graphiqueGCP;
import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Utils.Session;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Objects;

public class FD extends Application {

    private TextField ageField;
    private TextField butField;
    private TextField niveauPhysiqueField;
    private TextField maladieChroniqueField;
    private TextField nombreHeureField;
    private TextField idUserField;
    private TextField idOffreField;
    private TextField lesjoursFiled;
    private TextField horaireFiled;
    private TextField nomField;
    private ImageView backgroundImag;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Formulaire d'ajout d'une demande ");

        VBox cardContainer = new VBox();
        cardContainer.getStyleClass().add("card-container");
        cardContainer.setPadding(new Insets(20));
        cardContainer.setSpacing(10);




        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setStyle("-fx-background-color: rgb(250,247,221); -fx-padding: 25; -fx-border-radius: 10; -fx-background-radius: 10; -fx-alignment: center;");

        Label formTitle = new Label("Formulaire d'ajout d'une demande");
        formTitle.getStyleClass().add("form-title");

        Label nomLabel = new Label("Nom:");
        GridPane.setConstraints(nomLabel, 0, 0);
        nomField = new TextField();
        GridPane.setConstraints(nomField, 1, 0);

        Label ageLabel = new Label("Age:");
        GridPane.setConstraints(ageLabel, 0, 1);
        ageField = new TextField();
        GridPane.setConstraints(ageField, 1, 1);

        Label butLabel = new Label("But:");
        GridPane.setConstraints(butLabel, 0, 2);
        butField = new TextField();
        GridPane.setConstraints(butField, 1, 2);

        Label niveauPhysiqueLabel = new Label("Niveau Physique:");
        GridPane.setConstraints(niveauPhysiqueLabel, 0, 3);
        niveauPhysiqueField = new TextField();
        GridPane.setConstraints(niveauPhysiqueField, 1, 3);

        Label maladieChroniqueLabel = new Label("Maladie Chronique:");
        GridPane.setConstraints(maladieChroniqueLabel, 0, 4);
        maladieChroniqueField = new TextField();
        GridPane.setConstraints(maladieChroniqueField, 1, 4);

        Label nombreHeureLabel = new Label("Nombre d'Heures:");
        GridPane.setConstraints(nombreHeureLabel, 0, 5);
        nombreHeureField = new TextField();
        GridPane.setConstraints(nombreHeureField, 1, 5);

        Label idUserLabel = new Label("Identifiant User:");
        GridPane.setConstraints(idUserLabel, 0, 6);
        idUserField = new TextField();
        GridPane.setConstraints(idUserField, 1, 6);

        Label idOffreLabel = new Label("numéro d'Offre:");
        GridPane.setConstraints(idOffreLabel, 0, 7);
        idOffreField = new TextField();
        GridPane.setConstraints(idOffreField, 1, 7);

        Label etatLabel = new Label("Etat:");
        grid.add(etatLabel, 0, 8);

        TextField etatField = new TextField("En Attente");
        etatField.setEditable(false);
        grid.add(etatField, 1, 8);

        Label horaireLabel = new Label("Horaire:");
        GridPane.setConstraints(horaireLabel, 0, 9);
        horaireFiled = new TextField();
        GridPane.setConstraints(horaireFiled, 1, 9);

        Label lesjoursLabel = new Label("Lesjours:");
        GridPane.setConstraints(lesjoursLabel, 0, 10);
        lesjoursFiled = new TextField();
        GridPane.setConstraints(lesjoursFiled, 1, 10);

        Button sendButton = new Button("Envoyer");
        GridPane.setConstraints(sendButton, 1, 11);
        sendButton.setOnAction(event -> insertDemande());

        Button consulterButton = new Button("Consulter liste de demande");
        GridPane.setConstraints(consulterButton, 2, 11);
        consulterButton.setOnAction(event -> {
            try {
                consulterDemandes();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        grid.getChildren().add(consulterButton);



        grid.getChildren().addAll(ageLabel, ageField, butLabel, butField, niveauPhysiqueLabel, niveauPhysiqueField,
                maladieChroniqueLabel, maladieChroniqueField, nombreHeureLabel, nombreHeureField, idUserLabel,
                idUserField, idOffreLabel, idOffreField, lesjoursLabel, lesjoursFiled, horaireLabel, horaireFiled,nomLabel,nomField,
                sendButton);




        ageField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageField.setStyle("-fx-text-inner-color: red;");
            } else {
                ageField.setStyle("-fx-text-inner-color: black;");
            }
        });

        nombreHeureField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                nombreHeureField.setStyle("-fx-text-inner-color: red;");
            } else {
                nombreHeureField.setStyle("-fx-text-inner-color: black;");
            }
        });

        idUserField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idUserField.setStyle("-fx-text-inner-color: red;");
            } else {
                idUserField.setStyle("-fx-text-inner-color: black;");
            }
        });

        idOffreField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idOffreField.setStyle("-fx-text-inner-color: red;");
            } else {
                idOffreField.setStyle("-fx-text-inner-color: black;");
            }
        });

        // Appel de la méthode pour configurer la validation du champ horaireFiled
        setupTimeValidation(horaireFiled);
        // Appel de la méthode pour configurer la validation des champs de type String
        setupStringValidation(nomField);
        setupStringValidation(maladieChroniqueField);
        setupStringValidation(butField);
        setupStringValidation(niveauPhysiqueField);
        setupStringValidation(lesjoursFiled);

        Scene scene = new Scene(grid, 700, 650);

        scene.getStylesheets().add(getClass().getResource("/Styles/StyleAR.css").toExternalForm());
        primaryStage.setTitle("Formulaire de demande");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private boolean validateFields() {
        if (ageField.getText().isEmpty() || butField.getText().isEmpty() || niveauPhysiqueField.getText().isEmpty()
                || maladieChroniqueField.getText().isEmpty() || nombreHeureField.getText().isEmpty()
                || idUserField.getText().isEmpty() || idOffreField.getText().isEmpty() || lesjoursFiled.getText().isEmpty()
                || horaireFiled.getText().isEmpty()) {
            System.out.println("Veuillez remplir tous les champs.");
            return false;
        }
        return true;
    }


    private void setupTimeValidation(TextField timeField) {
        timeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                // Vérifie si la nouvelle valeur ne correspond pas au format de temps HH:mm
                timeField.setStyle("-fx-text-inner-color: red;");
            } else {
                timeField.setStyle("-fx-text-inner-color: black;");
            }
        });
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

    private void insertDemande() {
        Session session = Session.getInstance();
        User loggedInUser = session.getLoggedInUser();

        if (loggedInUser == null) {
            showAlert("Erreur", "Aucun utilisateur connecté !");
            return;
        }

        // Vérifier si l'utilisateur connecté a le rôle de membre
        if (!loggedInUser.getRole().equals(Role.MEMBRE)) {
            showAlert("Erreur", "Vous devez être membre pour insérer une demande !");
            return;
        }

        if (!checkEmptyFields()) {
            showAlertt("Champs vides", "Veuillez remplir tous les champs !");
            return;
        }

        String specialite = getSpecialiteFromOffreId(Integer.parseInt(idOffreField.getText()));

        if (!isMaladieAllowed(specialite, maladieChroniqueField.getText())) {
            showAlert("Impossible de participer", "Vous ne pouvez pas participer en raison de votre maladie.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO demande (nom,age, but, niveau_physique, maladie_chronique, nombreheure, user_id, offre_id, etat, horaire, lesjours) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            String etat = "En Attente";
            java.sql.Time horaire = java.sql.Time.valueOf("08:00:00");
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nomField.getText());
            statement.setInt(2, Integer.parseInt(ageField.getText()));
            statement.setString(3, butField.getText());
            statement.setString(4, niveauPhysiqueField.getText());
            statement.setString(5, maladieChroniqueField.getText());
            statement.setInt(6, Integer.parseInt(nombreHeureField.getText()));
            statement.setInt(7, Integer.parseInt(idUserField.getText()));
            statement.setInt(8, Integer.parseInt(idOffreField.getText()));
            statement.setString(9, etat);
            statement.setTime(10, horaire);
            statement.setString(11, lesjoursFiled.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Demande insérée avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de la demande: " + e.getMessage());
            setInvalidStyle();
        }
    }


    // Méthode pour vérifier si des champs sont vides
    private boolean checkEmptyFields() {
        if (nomField.getText().isEmpty() || ageField.getText().isEmpty() || butField.getText().isEmpty() ||
                niveauPhysiqueField.getText().isEmpty() || maladieChroniqueField.getText().isEmpty() ||
                nombreHeureField.getText().isEmpty() || idUserField.getText().isEmpty() || idOffreField.getText().isEmpty() ||
                lesjoursFiled.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    // Méthode pour afficher un message d'alerte dans l'interface utilisateur



    // Méthode pour vérifier si des champs sont vides
//    private boolean checkEmptyFields() {
//        if (nomField.getText().isEmpty() || ageField.getText().isEmpty() || butField.getText().isEmpty() ||
//                niveauPhysiqueField.getText().isEmpty() || maladieChroniqueField.getText().isEmpty() ||
//                nombreHeureField.getText().isEmpty() || idUserField.getText().isEmpty() || idOffreField.getText().isEmpty() ||
//                lesjoursFiled.getText().isEmpty()) {
//            showAlert("Champs vides", "Veuillez remplir tous les champs !");
//            return false;
//        }
//        return true;
//    }

    // Méthode pour gérer l'événement du bouton "Consulter"
    private void consulterDemandes() throws SQLException {
        // Ouvrir la fenêtre de consultation des demandes sans demander le nom du client
        ConsultationDemandes consultationDemandes = new ConsultationDemandes();
        consultationDemandes.start(new Stage());
    }
    private String getSpecialiteFromOffreId(int idOffre) {
        String url = "jdbc:mysql://localhost:3306/pidevgym";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT specialite FROM offre WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, idOffre);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("specialite");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la spécialité depuis la base de données: " + e.getMessage());
        }

        return null;  // Retourne null si la spécialité n'est pas trouvée ou en cas d'erreur
    }

    private boolean isMaladieAllowed(String specialite, String maladie) {
        if (specialite == null || maladie == null) {
            return false;  // Si la spécialité ou la maladie est nulle, la participation n'est pas autorisée
        }

        switch (specialite) {
            case "Yoga":
                return !maladie.equals("Maladies infectieuses contagieuses")
                        && !maladie.equals("Maladies oculaires graves")
                        && !maladie.equals("Troubles musculo-squelettiques graves")
                        && !maladie.equals("Problèmes neurologiques")
                        && !maladie.equals("Hypertension artérielle non contrôlée")
                        && !maladie.equals("Problèmes cardiaques graves");

            case "Boxe":
                return !maladie.equals("Problèmes cardiaques graves")
                        && !maladie.equals("Hypertension artérielle non contrôlée")
                        && !maladie.equals("Problèmes musculo-squelettiques graves")
                        && !maladie.equals("Maladies inflammatoires")
                        && !maladie.equals("Maladies infectieuses")
                        && !maladie.equals("Problèmes respiratoires graves")
                        && !maladie.equals("Troubles de l'alimentation");

            case "Musculation":
                return !maladie.equals("Maladies cardiaques graves")
                        && !maladie.equals("Hypertension artérielle non contrôlée")
                        && !maladie.equals("Problèmes respiratoires sévères")
                        && !maladie.equals("Maladies vasculaires périphériques")
                        && !maladie.equals("Problèmes neurologiques graves")
                        && !maladie.equals("Diabète non contrôlé")
                        && !maladie.equals("Infections actives");

            case "Cardio":
                return !maladie.equals("Maladies cardiaques graves")
                        && !maladie.equals("Hypertension artérielle non contrôlée")
                        && !maladie.equals("Problèmes respiratoires sévères")
                        && !maladie.equals("Maladies vasculaires périphériques")
                        && !maladie.equals("Problèmes neurologiques graves")
                        && !maladie.equals("Diabète non contrôlé")
                        && !maladie.equals("Infections actives");

            default:
                return true;  // Si la spécialité n'est pas reconnue, autorise la participation par défaut
        }
    }

    private void setInvalidStyle() {
        nomField.setStyle("-fx-border-color: initial;");
        ageField.setStyle("-fx-border-color: initial;");
        butField.setStyle("-fx-border-color: initial;");
        niveauPhysiqueField.setStyle("-fx-border-color: initial;");
        maladieChroniqueField.setStyle("-fx-border-color: initial;");
        nombreHeureField.setStyle("-fx-border-color: initial;");
        idUserField.setStyle("-fx-border-color: initial;");
        idOffreField.setStyle("-fx-border-color: initial;");
        lesjoursFiled.setStyle("-fx-border-color: initial;");
        horaireFiled.setStyle("-fx-border-color: initial;");

        nomField.setStyle("-fx-border-color: initial;");
        ageField.setStyle("-fx-border-color: red;");
        butField.setStyle("-fx-border-color: red;");
        niveauPhysiqueField.setStyle("-fx-border-color: red;");
        maladieChroniqueField.setStyle("-fx-border-color: red;");
        nombreHeureField.setStyle("-fx-border-color: red;");
        idUserField.setStyle("-fx-border-color: red;");
        idOffreField.setStyle("-fx-border-color: red;");
        lesjoursFiled.setStyle("-fx-border-color: red;");
        horaireFiled.setStyle("-fx-border-color: red;");
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlertt(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

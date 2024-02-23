package com.example.bty.Controllers.CourController;
import com.example.bty.Entities.User;
import com.example.bty.Entities.Cours;
import com.example.bty.Services.ServiceCours;
import com.example.bty.Utils.ConnexionDB;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class consulter extends Application {
    private static Connection connection;
    private ServiceCours serviceCours = new ServiceCours();
    private TableView<Cours> tableView = new TableView<>();

    public consulter() {
        connection = ConnexionDB.getInstance().getConnexion();

    }

    private ObservableList<Cours> coursList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        coursList = FXCollections.observableArrayList();
        TableView<Cours> tableView = new TableView<>(coursList);
        tableView.setPrefWidth(800);

        // Création des colonnes
        TableColumn<Cours, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Cours, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Cours, String> dureeColumn = new TableColumn<>("Durée");
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));

        TableColumn<Cours, String> intensiteColumn = new TableColumn<>("Intensité");
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("intensite"));

        TableColumn<Cours, String> cibleColumn = new TableColumn<>("Cible");
        cibleColumn.setCellValueFactory(new PropertyValueFactory<>("cible"));

        TableColumn<Cours, String> categorieColumn = new TableColumn<>("Catégorie");
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        TableColumn<Cours, String> objectifColumn = new TableColumn<>("Objectif");
        objectifColumn.setCellValueFactory(new PropertyValueFactory<>("objectif"));

        TableColumn<Cours, Boolean> etatColumn = new TableColumn<>("État");
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));

        TableColumn<Cours, Integer> capaciteColumn = new TableColumn<>("Capacité");
        capaciteColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        TableColumn<Cours, Integer> coachIdColumn = new TableColumn<>("ID Coach");
        coachIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCoach().getId()).asObject());

        TableColumn<Cours, String> coachNameColumn = new TableColumn<>("Nom du Coach");
        coachNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCoach().getName()));

        TableColumn<Cours, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(100);
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final ImageView modifierIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/bty/imagesModuleCours/modifier.png")));
            private final ImageView supprimerIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/bty/imagesModuleCours/supprimer.png")));
            private final Button modifierButton = new Button("", modifierIcon);
            private final Button supprimerButton = new Button("", supprimerIcon);

            {
                modifierButton.setOnAction(event -> {
                    Cours cours = getTableView().getItems().get(getIndex());
                    updateCours(cours);
                });

                supprimerButton.setOnAction(event -> {
                    Cours cours = getTableView().getItems().get(getIndex());
                    confirmerSuppression(cours);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(modifierButton, supprimerButton);
                    buttons.setSpacing(5);
                    setGraphic(buttons);
                }
            }
        });

        tableView.getColumns().addAll(idColumn, nomColumn, dureeColumn, intensiteColumn, cibleColumn, categorieColumn,
                objectifColumn, etatColumn, capaciteColumn, coachIdColumn, coachNameColumn, actionsColumn);

        VBox root = new VBox(tableView);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Consultation des cours");
        primaryStage.show();

        // Chargement initial des cours
        reloadCours();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void reloadCours() {
        try {
            if (connection.isClosed()) {
                connection = ConnexionDB.getInstance().getConnexion(); // Réouvrir la connexion si elle est fermée
            }
            List<Cours> newList = consulterCours(connection);
            coursList.clear();
            coursList.addAll(newList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Cours> consulterCours(Connection connection) {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM cours";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Cours cr = new Cours();
                cr.setId(resultSet.getInt("id_cour"));
                cr.setNom(resultSet.getString("nomCour") + "    ");
                cr.setCategorie(resultSet.getString("Categorie") + "    ");
                cr.setCible(resultSet.getString("Cible") + "    ");
                cr.setDuree(resultSet.getString("Duree") + "    ");
                cr.setIntensite(resultSet.getString("Intensite") + "    ");
                cr.setObjectif(resultSet.getString("Objectif") + "    ");
                cr.setEtat(resultSet.getBoolean("etat"));
                cr.setCapacite(resultSet.getInt("Capacite"));
                int coachId = resultSet.getInt("id_user");

                String coachName = null;
                try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT nom FROM user WHERE id = ?")) {
                    preparedStatement.setInt(1, coachId);
                    try (ResultSet coachResultSet = preparedStatement.executeQuery()) {
                        if (coachResultSet.next()) {
                            coachName = coachResultSet.getString("nom");
                        }
                    }
                }

                User coach = new User();
                coach.setId(coachId);
                coach.setName(coachName);
                cr.setCoach(coach);

                coursList.add(cr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursList;
    }

    private void updateCours(Cours cours) {
        // Création de la fenêtre de modification
        Stage modificationStage = new Stage();
        modificationStage.setTitle("Modifier le cours");

        // Champ de texte pour le nom du cours
        TextField nomField = new TextField(cours.getNom());

        // Champ de texte pour le reste des champs
        TextField dureeField = new TextField(cours.getDuree());
        TextField intensiteField = new TextField(cours.getIntensite());
        TextField cibleField = new TextField(cours.getCible());
        TextField categorieField = new TextField(cours.getCategorie());
        TextField objectifField = new TextField(cours.getObjectif());
        CheckBox etatCheckBox = new CheckBox("Actif");
        etatCheckBox.setSelected(cours.isEtat());
        TextField capaciteField = new TextField(String.valueOf(cours.getCapacite()));
        TextField idCoachField = new TextField(String.valueOf(cours.getCoach().getId()));

        // Bouton pour enregistrer les modifications
        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(event -> {
            // Code pour sauvegarder les modifications dans la base de données
            try (PreparedStatement statement = connection.prepareStatement(
                         "UPDATE cours SET nomCour = ?, Duree = ?, Intensite = ?, Cible = ?, Categorie = ?, Objectif = ?, etat = ?, capacite = ?, id_user = ? WHERE id_cour = ?")) {

                statement.setString(1, nomField.getText());
                statement.setString(2, dureeField.getText());
                statement.setString(3, intensiteField.getText());
                statement.setString(4, cibleField.getText());
                statement.setString(5, categorieField.getText());
                statement.setString(6, objectifField.getText());
                statement.setBoolean(7, etatCheckBox.isSelected());
                statement.setInt(8, Integer.parseInt(capaciteField.getText()));
                statement.setInt(9, Integer.parseInt(idCoachField.getText()));
                statement.setInt(10, cours.getId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Mise à jour réussie
                    cours.setNom(nomField.getText());
                    cours.setDuree(dureeField.getText());
                    cours.setIntensite(intensiteField.getText());
                    cours.setCible(cibleField.getText());
                    cours.setCategorie(categorieField.getText());
                    cours.setObjectif(objectifField.getText());
                    cours.setEtat(etatCheckBox.isSelected());
                    cours.setCapacite(Integer.parseInt(capaciteField.getText()));
                    cours.getCoach().setId(Integer.parseInt(idCoachField.getText()));
                    tableView.refresh();
                    reloadCours(); // Recharger les données des cours pour mettre à jour la TableView
                } else {
                    // Gérer l'échec de la mise à jour
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("La mise à jour a échoué");
                    alert.setContentText("Impossible de mettre à jour le cours. Veuillez réessayer.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            modificationStage.close(); // Fermeture de la fenêtre de modification après la mise à jour
        });

        // Mise en page de la fenêtre de modification
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                new Label("Nom du cours :"), nomField,
                new Label("Durée du cours :"), dureeField,
                new Label("Intensité du cours :"), intensiteField,
                new Label("Cible du cours :"), cibleField,
                new Label("Catégorie du cours :"), categorieField,
                new Label("Objectif du cours :"), objectifField,
                new Label("État du cours :"), etatCheckBox,
                new Label("Capacité du cours :"), capaciteField,
                new Label("ID du coach :"), idCoachField,
                saveButton
        );

        // Affichage de la fenêtre de modification
        Scene scene = new Scene(layout, 400, 500);
        modificationStage.setScene(scene);
        modificationStage.show();
    }


    private void confirmerSuppression(Cours cours) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce cours ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            supprimerCours(cours);
        }
    }

    private void supprimerCours(Cours cours) {
        try {
            // Vérifiez si la connexion est fermée et rouvrez-la si nécessaire
            if (connection == null || connection.isClosed()) {
                connection = ConnexionDB.getInstance().getConnexion();
            }

            // Utilisez un bloc try-with-resources pour gérer les ressources JDBC
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM cours WHERE id_cour = ?")) {
                statement.setInt(1, cours.getId());
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    coursList.remove(cours);
                } else {
                    // Gérer l'échec de la suppression
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("La suppression a échoué");
                    alert.setContentText("Impossible de supprimer le cours. Veuillez réessayer.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        super.stop();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }}
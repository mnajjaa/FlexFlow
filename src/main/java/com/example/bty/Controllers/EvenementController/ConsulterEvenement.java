package com.example.bty.Controllers.EvenementController;




import com.example.bty.Entities.Evenement;
import com.example.bty.Entities.User;
import com.example.bty.Services.ServiceEvenement;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ConsulterEvenement extends Application {
    private static Connection connection;
    private ServiceEvenement serviceCours = new ServiceEvenement();
    private TableView<Evenement> tableView = new TableView<>();


    public ConsulterEvenement() {
        connection = ConnexionDB.getInstance().getConnexion();

    }
    private ObservableList<Evenement> EvenementList;


    @Override
    public void start(Stage primaryStage) throws Exception {
        EvenementList = FXCollections.observableArrayList();
        TableView<Evenement> tableView = new TableView<>(EvenementList);
        tableView.setPrefWidth(800);
        // Création des colonnes de la TableView
        TableColumn<Evenement, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Evenement, String> NomColumn = new TableColumn<>("Nom");
        NomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Evenement, String> categorieColumn = new TableColumn<>("Catégorie");
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        TableColumn<Evenement, String> ObjectifColumn = new TableColumn<>("Objectif");
        ObjectifColumn.setCellValueFactory(new PropertyValueFactory<>("Objectif"));

        TableColumn<Evenement, Integer> NbrColumn = new TableColumn<>("N°Place");
        NbrColumn.setCellValueFactory(new PropertyValueFactory<>("nbre_place"));

        TableColumn<Evenement, Date> DateColumn = new TableColumn<>("Date");
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        TableColumn<Evenement, Time> TimeColumn = new TableColumn<>("Time");
        TimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));


        TableColumn<Evenement, Integer> coachIdColumn = new TableColumn<>("ID Coach");
        coachIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCoach().getId()).asObject());

        TableColumn<Evenement, String> coachNameColumn = new TableColumn<>("Nom du Coach");
        coachNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCoach().getName()));

        TableColumn<Evenement, Boolean> etatColumn = new TableColumn<>("etat");
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
//


        TableColumn<Evenement, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(100);
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final ImageView modifierIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/bty/imagesModuleEvenement/edit.png")));
            private final ImageView supprimerIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/bty/imagesModuleEvenement/delete.png")));
            private final Button modifierButton = new Button("", modifierIcon);
            private final Button supprimerButton = new Button("", supprimerIcon);

            {
                modifierButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    updateCours(evenement);
                });

                supprimerButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    confirmerSuppression(evenement);
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

        tableView.getColumns().addAll(idColumn,NomColumn, categorieColumn, ObjectifColumn,NbrColumn,DateColumn,TimeColumn,coachIdColumn,coachNameColumn,etatColumn,actionsColumn);

        VBox root = new VBox(tableView);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Consultation des Evenements");
        primaryStage.show();

        // Chargement initial des cours
        reloadEvenements();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private void reloadEvenements() {
        try {
            if (connection.isClosed()) {
                connection = ConnexionDB.getInstance().getConnexion(); // Réouvrir la connexion si elle est fermée
            }
            List<Evenement> newList = consulterEvenement(connection);
            EvenementList.clear();
            EvenementList.addAll(newList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Evenement> consulterEvenement(Connection connection) {
        List<Evenement> EvenementList = new ArrayList<>();
        String query = "SELECT * FROM Evenement";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Evenement E = new Evenement();

                E.setId(resultSet.getInt("id_evenement"));
                E.setNom(resultSet.getString("nomEvenement")+" ");
                E.setCategorie(resultSet.getString("categorie")+" ");
                E.setObjectif(resultSet.getString("Objectif")+"  ");
                E.setNbre_place(Integer.parseInt(resultSet.getInt("nbrPlace") + "".trim()));
//                E.setDate(Date.valueOf(resultSet.getDate("Date")+"  "));
                E.setDate(resultSet.getDate("Date"));

                E.setTime(resultSet.getTime("Time"));
                E.setEtat(resultSet.getBoolean("etat"));

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
                E.setCoach(coach);

                EvenementList.add(E);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return EvenementList;
    }


    private void updateCours(Evenement evenement) {
        // Création de la fenêtre de modification
        Stage modificationStage = new Stage();
        modificationStage.setTitle("Modifier le cours");

        TextField nomField = new TextField(evenement.getNom());
        TextField CategorieField = new TextField(evenement.getCategorie());
        TextField objectifField = new TextField(evenement.getObjectif());
        TextField nbrPlaceField = new TextField(String.valueOf(evenement.getNbre_place()));
        DatePicker DateField = new DatePicker(evenement.getDate().toLocalDate());
        TextField TimeField = new TextField(evenement.getTime().toString());
        CheckBox etatCheckBox = new CheckBox("Actif");
        etatCheckBox.setSelected(evenement.isEtat());
        TextField idCoachField = new TextField(String.valueOf(evenement.getCoach().getId()));

        // Bouton pour enregistrer les modifications
        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(event -> {
            // Code pour sauvegarder les modifications dans la base de données
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE evenement SET nomEvenement = ?, categorie = ?, Objectif = ?, nbrPlace = ?, Date = ?, Time = ?,etat = ? ,id_user = ?  WHERE id_evenement  = ?")) {

                statement.setString(1, nomField.getText());
                statement.setString(2, CategorieField.getText());
                statement.setString(3, objectifField.getText());
                statement.setString(4, nbrPlaceField.getText());
                statement.setDate(5, Date.valueOf(DateField.getValue()));
                statement.setString(6, TimeField.getText());
                statement.setBoolean(7, etatCheckBox.isSelected());
                statement.setInt(8, Integer.parseInt(idCoachField.getText()));
                statement.setInt(9, evenement.getId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Mise à jour réussie
                    evenement.setNom(nomField.getText());
                    evenement.setCategorie(CategorieField.getText());
                    evenement.setObjectif(objectifField.getText());
                    evenement.setNbre_place(Integer.parseInt(nbrPlaceField.getText()));
                    evenement.setDate(java.sql.Date.valueOf(DateField.getValue()));
                    evenement.setTime(Time.valueOf(TimeField.getText()));
                    evenement.setEtat(etatCheckBox.isSelected());

                    evenement.getCoach().setId(Integer.parseInt(idCoachField.getText()));
                    tableView.refresh();
                    reloadEvenements(); // Recharger les données des cours pour mettre à jour la TableView
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
                new Label("Durée du cours :"), CategorieField,
                new Label("Intensité du cours :"), objectifField,
                new Label("Cible du cours :"), nbrPlaceField,
                new Label("Catégorie du cours :"), DateField,
                new Label("Objectif du cours :"), TimeField,
                new Label("État du cours :"), etatCheckBox,
                new Label("ID du coach :"), idCoachField,
                saveButton
        );

        // Affichage de la fenêtre de modification
        Scene scene = new Scene(layout, 400, 500);
        modificationStage.setScene(scene);
        modificationStage.show();
    }


    private void confirmerSuppression(Evenement evenement) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce evenement ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            supprimerCours(evenement);
        }
    }

    private void supprimerCours(Evenement evenement) {
        try {
            // Vérifiez si la connexion est fermée et rouvrez-la si nécessaire
            if (connection == null || connection.isClosed()) {
                connection = ConnexionDB.getInstance().getConnexion();
            }

            // Utilisez un bloc try-with-resources pour gérer les ressources JDBC
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM evenement WHERE id_evenement = ?")) {
                statement.setInt(1, evenement.getId());
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    EvenementList.remove(evenement);
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
    }

}


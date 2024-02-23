package com.example.bty.Controllers;

import com.example.bty.Controllers.ProduitController.ActionButtonTableCell;
import com.example.bty.Entities.Produit;
import com.example.bty.Services.ServiceProduit;
import com.example.bty.Utils.ConnexionDB;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class DashboardController {

    public Button produit_btn;
    @FXML
    private TableView<Produit> tableView;

    @FXML
    private TableColumn<Produit, Integer> idCol;

    @FXML
    private TableColumn<Produit, String> nomCol;

    @FXML
    private TableColumn<Produit, String> descriptionCol;

    @FXML
    private TableColumn<Produit, Double> prixCol;

    @FXML
    private TableColumn<Produit, String> typeCol;

    @FXML
    private TableColumn<Produit, Integer> quantiteCol;

    @FXML
    private TableColumn<Produit, Integer> quantiteVenduesCol;

    @FXML
    private TableColumn<Produit, Void> actionColumn;

    // Autres déclarations
    private Connection connexion;
    private PreparedStatement pst;
    private Statement ste ;
    public DashboardController() {
        connexion = ConnexionDB.getInstance().getConnexion();
    }
    private ServiceProduit produitServices = new ServiceProduit();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdProduit()).asObject());
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        descriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        prixCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        quantiteCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());
        quantiteVenduesCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteVendues()).asObject());

        // Ajout de la colonne Actions
        actionColumn.setCellFactory(new ActionButtonTableCellFactory<>(
                (Produit produit) -> {
                    // Logique de suppression
                    if (showConfirmationDialog("Suppression", "Voulez-vous supprimer ce produit ?")) {
                        if (produitServices.supprimerProduit(produit.getIdProduit())) {
                            showAlert("Success", "La suppression a réussi");
                            actualiserTable();
                        } else {
                            showAlert("Échec", "La suppression a échoué");
                        }
                    }
                },
                (Produit produit) -> {
                    // Logique d'édition
                    openEditForm(produit);
                }
        ));

        // Chargement initial des données dans la table
        actualiserTable();
        produit_btn.setOnAction(this::handleProduitBtnClick);



    }

    @FXML
    private void handleProduitBtnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardProduit.fxml"));
            Parent dashboardProduitView = loader.load();

            // Si vous avez besoin d'accéder au contrôleur du tableau de bord produit, vous pouvez le faire ici
            // DashboardProduitController dashboardProduitController = loader.getController();
            // dashboardProduitController.initData();

            Stage stage = new Stage();
            stage.setTitle("Dashboard Produit");
            stage.setScene(new Scene(dashboardProduitView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement de la vue
        }
    }










    private void actualiserTable() {
        // Appeler la méthode consulterProduits et charger les données dans la table
        List<Produit> produits = consulterProduits();
        tableView.getItems().setAll(produits);
    }

    // Autres méthodes

    private List<Produit> consulterProduits() {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit";
        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Produit produit = new Produit();
                produit.setIdProduit(resultSet.getInt("idProduit"));
                produit.setNom(resultSet.getString("nom"));
                produit.setDescription(resultSet.getString("Description"));
                produit.setPrix(resultSet.getDouble("Prix"));
                produit.setType(resultSet.getString("Type"));
                produit.setQuantite(resultSet.getInt("Quantite"));
                produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }
    private void openEditForm(Produit produit) {
        Dialog<Produit> editDialog = new Dialog<>();
        editDialog.setTitle("Modifier le produit");
        editDialog.setHeaderText(null);

        // Créer des champs de formulaire pour les attributs du produit
        TextField nomField = new TextField(produit.getNom());
        TextField descriptionField = new TextField(produit.getDescription());
        TextField prixField = new TextField(String.valueOf(produit.getPrix()));
        TextField typeField = new TextField(produit.getType());
        TextField quantiteField = new TextField(String.valueOf(produit.getQuantite()));
        TextField quantiteVenduesField = new TextField(String.valueOf(produit.getQuantiteVendues()));






        // Créer le contenu du dialogue
        VBox content = new VBox();
        content.setSpacing(10);
        content.getChildren().addAll(
                new Label("Nom: "), nomField,
                new Label("Description: "), descriptionField,
                new Label("Prix: "), prixField,
                new Label("Type: "), typeField,
                new Label("Quantité: "), quantiteField,
                new Label("Quantité Vendue: "), quantiteVenduesField
        );

        // Ajouter les boutons au dialogue :
        ButtonType modifierButton = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        editDialog.getDialogPane().getButtonTypes().addAll(modifierButton, cancelButton);

        // editDialog.getDialogPane().getButtonTypes().addAll(modifierButton, ButtonType.CANCEL);

        Node modifierButtonNode = editDialog.getDialogPane().lookupButton(modifierButton);
        Node cancelButtonNode = editDialog.getDialogPane().lookupButton(cancelButton);



        editDialog.getDialogPane().setContent(content);

        // Définir la logique de conversion du résultat du dialogue
        editDialog.setResultConverter(dialogButton -> {
            if (dialogButton == modifierButton) {
                Produit editedProduit = new Produit();
                editedProduit.setIdProduit(produit.getIdProduit());
                editedProduit.setNom(nomField.getText());
                editedProduit.setDescription(descriptionField.getText());
                editedProduit.setPrix(Double.parseDouble(prixField.getText()));
                editedProduit.setType(typeField.getText());
                editedProduit.setQuantite(Integer.parseInt(quantiteField.getText()));
                editedProduit.setQuantiteVendues(Integer.parseInt(quantiteVenduesField.getText()));
                return editedProduit;
            }
            return null;
        });

        // Afficher le dialogue et traiter le résultat
        Optional<Produit> result = editDialog.showAndWait();
        result.ifPresent(editedProduit -> {
            // Mettre à jour le produit dans la base de données
            modifierProduit(editedProduit);
            actualiserTable();
        });
    }

    private void modifierProduit(Produit produit) {
        // Mettre à jour le produit dans la base de données en utilisant votre service
        produitServices.modifierProduit(produit);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /*private void actualiserTable() {
        ObservableList<Produit> produits = FXCollections.observableArrayList(produitServices.consulterProduits());
        tableView.setItems(produits);
    }*/

    private boolean showConfirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType yesButton = new ButtonType("Oui");
        ButtonType noButton = new ButtonType("Non");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    public class ActionButtonTableCellFactory<S> implements Callback<TableColumn<S, Void>, TableCell<S, Void>> {
        private final Consumer<S> deleteAction;
        private final Consumer<S> editAction;

        public ActionButtonTableCellFactory(Consumer<S> deleteAction, Consumer<S> editAction) {
            this.deleteAction = deleteAction;
            this.editAction = editAction;
        }

        @Override
        public TableCell<S, Void> call(TableColumn<S, Void> param) {
            return new ActionButtonTableCell<>(deleteAction, editAction);
        }
    }
}

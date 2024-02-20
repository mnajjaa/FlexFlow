package com.example.bty.Controllers.ProduitController;


import com.example.bty.Entities.Produit;
import com.example.bty.Services.ServiceProduit;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConsulterProduit extends Application {

    private ServiceProduit produitServices = new ServiceProduit();
    private TableView<Produit> tableView = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion des Produits");

        // Création des colonnes de la TableView
        TableColumn<Produit, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProduitProperty().asObject());

        TableColumn<Produit, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(cellData -> cellData.getValue().nomProperty());

        TableColumn<Produit, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        TableColumn<Produit, Double> prixCol = new TableColumn<>("Prix");
        prixCol.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());

        TableColumn<Produit, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        TableColumn<Produit, Integer> quantiteCol = new TableColumn<>("Quantité");
        quantiteCol.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty().asObject());

        TableColumn<Produit, Integer> quantiteVenduesCol = new TableColumn<>("Quantité Vendue");
        quantiteVenduesCol.setCellValueFactory(cellData -> cellData.getValue().quantiteVenduesProperty().asObject());

        // Ajoutez cette ligne à la méthode start de votre classe principale
      /*  TableColumn<Produit, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(new ActionButtonTableCellFactory<>());
        tableView.getColumns().add(actionColumn);*/


        tableView.getColumns().addAll(idCol, nomCol, descriptionCol, prixCol, typeCol, quantiteCol, quantiteVenduesCol);

// Ajoutez cette ligne à la fin des colonnes, après les autres colonnes
        TableColumn<Produit, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(new ActionButtonTableCellFactory<>());
        tableView.getColumns().add(actionColumn);

        // Création des boutons pour effectuer les opérations CRUD
        Button actualiserBtn = new Button("Actualiser");
        actualiserBtn.setOnAction(e -> actualiserTable());

        VBox vbox = new VBox();
        vbox.getStyleClass().add("container");
        vbox.getChildren().addAll(actualiserBtn, tableView);

        Scene scene = new Scene(vbox, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/CSSmoduleProduit/ConsulterProduit.css").toExternalForm()); // Chemin vers le fichier CSS
        primaryStage.setScene(scene);
        primaryStage.show();

        actualiserTable();
    }

    private void actualiserTable() {
        // Utilisez produitServices.consulterProduits() pour obtenir la liste des produits
        ObservableList<Produit> produits = FXCollections.observableArrayList(produitServices.consulterProduits());
        tableView.setItems(produits);
    }


    public class ActionButtonTableCellFactory<S> implements Callback<TableColumn<S, Void>, TableCell<S, Void>> {
        @Override
        public TableCell<S, Void> call(TableColumn<S, Void> param) {
            return new ActionButtonTableCell<>();
        }
    }

    private void showAlert(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
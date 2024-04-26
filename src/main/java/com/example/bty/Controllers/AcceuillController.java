package com.example.bty.Controllers;

import com.example.bty.Controllers.ProduitController.ActionButtonTableCell;
import com.example.bty.Controllers.ProduitController.Ajoutproduit;
import com.example.bty.Entities.Commmande;
import com.example.bty.Entities.Produit;
import com.example.bty.Services.ServiceProduit;
import com.example.bty.Utils.ConnexionDB;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class AcceuillController {


    @FXML
    private AnchorPane dashboardAnchorPane;


    @FXML
    private LineChart<String, Number> chart;




    private Connection connexion;
    private PreparedStatement pst;
    private Statement ste ;
    public AcceuillController() {
        connexion = ConnexionDB.getInstance().getConnexion();
    }
    private ServiceProduit produitServices = new ServiceProduit();

    @FXML
    private void initialize() {









                // Chargement initial des données dans la table


        // Button ajoutProduitButton = new Button("Ajout");




        // Appeler le service pour obtenir le chiffre d'affaires

        chargerDonneesGraphique();




    }




















    @FXML
    private void chargerDonneesGraphique() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Chiffre d'affaires par jour");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT dateCommande, SUM(montant) FROM commande GROUP BY dateCommande")) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("dateCommande");
                Date date = new Date(timestamp.getTime());

                double montant = resultSet.getDouble("SUM(montant)");

                series.getData().add(new XYChart.Data<>(dateFormat.format(date), montant));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dashboardAnchorPane.getChildren().add(chart); // Ajoutez le graphique à l'AnchorPane
        chart.getData().add(series);

        // Ajouter la légende personnalisée
        Circle legendCircle = new Circle(5, Color.BLUE); // Ajustez la couleur du cercle selon vos préférences
        Label legendLabel = new Label("Chiffre d'affaires par jour");
        legendLabel.setTextFill(Color.BLACK); // Ajustez la couleur du texte selon vos préférences

        HBox legendBox = new HBox(10, legendCircle, legendLabel);
        legendBox.setAlignment(Pos.CENTER);

        dashboardAnchorPane.getChildren().add(legendBox);
        AnchorPane.setBottomAnchor(legendBox, 18.0); // Ajustez la position verticale selon vos préférences
        AnchorPane.setLeftAnchor(legendBox, (dashboardAnchorPane.getWidth() + 1000) / 2); // Ajustez la position horizontale selon vos préférences
    }
















   /* private void actualiserTable() {
        // Appeler la méthode consulterProduits et charger les données dans la table
        List<Produit> produits = consulterProduits();
        tableView.getItems().setAll(produits);
    }*/

    // Autres méthodes









    /*private void actualiserTable() {
        ObservableList<Produit> produits = FXCollections.observableArrayList(produitServices.consulterProduits());
        tableView.setItems(produits);
    }*/




}



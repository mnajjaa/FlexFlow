package com.example.bty.Controllers.graphiqueGCP;

import com.example.bty.Entities.Etat;
import com.example.bty.Entities.Offre;
import com.example.bty.Entities.Produit;
import com.example.bty.Entities.Specialite;
import com.example.bty.Services.ServiceOffre;
import com.example.bty.Utils.ConnexionDB;
import javafx.beans.property.*;
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

public class Dashbordadmin {

    public Button ajout_produit;
    public Button actuliser_produit;
    public Label dashboard_NM;

    @FXML
    private TableView<ConsultationOffre.OffreItem> tableView;



    @FXML
    private TableColumn<ConsultationOffre.OffreItem, String> nomCol;
    @FXML
    public TableColumn<ConsultationOffre.OffreItem, String> specialiteCol;
    @FXML
    public TableColumn<ConsultationOffre.OffreItem, String> tarifnCol;
    @FXML
    public TableColumn<ConsultationOffre.OffreItem, String > idCoachCol;
    @FXML
    public TableColumn<ConsultationOffre.OffreItem, String> etatCol;



    @FXML
    private TableColumn<Offre, Void> actionColumn;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_NC;
    // Autres déclarations
    private Connection connexion;
    private PreparedStatement pst;
    private Statement ste ;
    public Dashbordadmin() {
        connexion = ConnexionDB.getInstance().getConnexion();
    }
    private ServiceOffre serviceOffre = new ServiceOffre();

    @FXML
    private void initialize() {
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        specialiteCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialite()));
        tarifnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTarif()));
        idCoachCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoach()));
        etatCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtat()));


        // Ajout de la colonne Actions
//        actionColumn.setCellFactory(new ActionButtonTableCellFactory<>(
//                (Produit produit) -> {
//                    // Logique de suppression
//                    if (showConfirmationDialog("Suppression", "Voulez-vous supprimer ce produit ?")) {
//                        if (produitServices.supprimerProduit(produit.getIdProduit())) {
//                            showAlert("Success", "La suppression a réussi");
//                            actualiserTable();
//                        } else {
//                            showAlert("Échec", "La suppression a échoué");
//                        }
//                    }
//                },
//                (Produit produit) -> {
//                    // Logique d'édition
//                    openEditForm(produit);
//                }
//        ));

        // Chargement initial des données dans la table
        actualiserTable();

        // Button ajoutProduitButton = new Button("Ajout");
//        ajout_produit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                // Appel de la méthode pour ouvrir la nouvelle fenêtre d'ajout de produit
//                ouvrirFenetreAjoutProduit();
//            }
//        });

//        actuliser_produit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                // Appel de la méthode pour ouvrir la nouvelle fenêtre d'ajout de produit
//                actualiserTable();
//            }
//        });


//        // Appeler le service pour obtenir le chiffre d'affaires
//        double chiffreAffaires = ServiceProduit.calculerChiffreAffaires();
//
//        // Mettre à jour le label avec le chiffre d'affaires
//        dashboard_TI.setText( chiffreAffaires + " Dnt" );
//
//
//
//        String produitPlusAchete = getProduitPlusAchete();
//
//// Mettre à jour le Label avec le nom du produit le plus acheté
//        if (produitPlusAchete != null) {
//            dashboard_NC.setText(produitPlusAchete);
//        } else {
//            dashboard_NC.setText("Aucun produit");
//        }

//
//        String produitMoinsAchete = getProduitMoinsVendu();
//
//// Mettre à jour le Label avec le nom du produit le plus acheté
//        if (produitPlusAchete != null) {
//            dashboard_NM.setText(produitMoinsAchete);
//        } else {
//            dashboard_NM.setText("Aucun produit");
//        }


    }




//    public String getProduitMoinsVendu() {
//        String produitMoinsVendu = null;
//
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "")) {
////            String sql = "SELECT nom FROM produit ORDER BY prix * quantite ASC LIMIT 1";
//            String sql = "SELECT nom FROM produit ORDER BY quantiteVendues ASC LIMIT 1";
//
//
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                 ResultSet resultSet = preparedStatement.executeQuery()) {
//
//                if (resultSet.next()) {
//                    produitMoinsVendu = resultSet.getString("nom");
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Gérer les erreurs de connexion ou de requête SQL ici
//        }
//
//        return produitMoinsVendu;
//    }
//
//
//
//    public String getProduitPlusAchete() {
//        String produitPlusAchete = null;
//        // Connexion à la base de données
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "")) {
//
//            // Requête SQL pour obtenir le produit le plus acheté
////            String sql = "SELECT nom FROM produit ORDER BY prix * quantite DESC LIMIT 1";
//            String sql = "SELECT nom FROM produit ORDER BY quantiteVendues DESC LIMIT 1";
//
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                 ResultSet resultSet = preparedStatement.executeQuery()) {
//
//                if (resultSet.next()) {
//                    produitPlusAchete = resultSet.getString("nom");
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Gérer les erreurs de connexion ou de requête SQL ici
//        }
//        return produitPlusAchete;
//    }

//    @FXML
//    private void ouvrirFenetreAjoutProduit() {
//        // Créer une nouvelle instance de la classe Ajoutproduit
//        Ajoutproduit ajoutproduit = new Ajoutproduit();
//
//        // Appeler la méthode start pour afficher la fenêtre d'ajout de produit
//        ajoutproduit.start(new Stage());
//    }


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




    @FXML
    private void actualiserTable() {
        ObservableList<ConsultationOffre.OffreItem>

            produits = FXCollections.observableArrayList(consulterOffre());

        tableView.setItems(produits);
    }


    public List<ConsultationOffre.OffreItem> consulterOffre() {
        List<ConsultationOffre.OffreItem> commandes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "")) {
            String query = "SELECT * FROM Offre"; // Assurez-vous que le nom de la table est correct

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {


                while (resultSet.next()) {
                    ConsultationOffre.OffreItem offreItem = new ConsultationOffre.OffreItem();

                    offreItem.setNom(resultSet.getString("nom"));
                    offreItem.setSpecialite(resultSet.getString("specialite"));
                    offreItem.setTarif(resultSet.getString("tarif_heure"));
                    offreItem.setCoach(resultSet.getString("id_Coach"));
                    offreItem.setEtat(resultSet.getString("etatOffre"));
                    commandes.add(offreItem);
                }



            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }



   /* private void actualiserTable() {
        // Appeler la méthode consulterProduits et charger les données dans la table
        List<Produit> produits = consulterProduits();
        tableView.getItems().setAll(produits);
    }*/

    // Autres méthodes








    private boolean validateFields(TextField... fields) {
        // Check if all fields are non-empty
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }




    private void showAlert1(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Ajoute un bouton OK personnalisé qui ne ferme pas la boîte de dialogue parente
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        // Affiche l'alerte
        alert.showAndWait();
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




}
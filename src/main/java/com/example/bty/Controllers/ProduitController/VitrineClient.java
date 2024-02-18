package com.example.bty.Controllers.ProduitController;

import com.example.bty.Entities.Commande;
import com.example.bty.Entities.Produit;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VitrineClient extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pidevgym";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private List<Produit> produits; // Liste des produits à afficher
    private Connection connection;

    private List<Commande> panier; // Panier du client

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Affichage des produits");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        VBox topBar = new VBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");
        topBar.setSpacing(5);

        Button consulterPanierButton = new Button("Consulter le Panier");
        consulterPanierButton.getStyleClass().add("consulter-panier-button");
        consulterPanierButton.setOnAction(e -> consulterPanier());
        topBar.getChildren().add(consulterPanierButton);


        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().add(consulterPanierButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);  // Aligner le conteneur à droite

        topBar.getChildren().add(buttonContainer);


        ComboBox<String> choixRechercheComboBox = new ComboBox<>();
        choixRechercheComboBox.getItems().addAll("Type");
        choixRechercheComboBox.setValue("Rechercher par :");

        TextField rechercheTextField = new TextField();
        rechercheTextField.setPromptText("Rechercher par type");

     /*   TextField prixMinField = new TextField();
        prixMinField.setPromptText("Prix minimum");

        TextField prixMaxField = new TextField();
        prixMaxField.setPromptText("Prix maximum");*/

        choixRechercheComboBox.setOnAction(e -> {
            String choix = choixRechercheComboBox.getValue();

            if ("Type".equals(choix)) {
                rechercheTextField.setVisible(true);
               // prixMinField.setVisible(false);
              //  prixMaxField.setVisible(false);
            }/* else if ("Plage de prix".equals(choix)) {
                rechercheTextField.setVisible(false);
                prixMinField.setVisible(true);
                prixMaxField.setVisible(true);
            }*/
        });

        rechercheTextField.setVisible(false);
      //  prixMinField.setVisible(false);
      //  prixMaxField.setVisible(false);

        Button rechercherButton = new Button("Rechercher");
        rechercherButton.getStyleClass().add("search-button");
        rechercherButton.setOnAction(e -> {
            String choixRecherche = choixRechercheComboBox.getValue();

            if ("Type".equals(choixRecherche)) {
                String typeRecherche = rechercheTextField.getText();
                List<Produit> produitsRecherches = rechercherProduits(typeRecherche);
                afficherProduits(produitsRecherches, root);
            } //else if ("Plage de prix".equals(choixRecherche)) {
               // int prixMin = Integer.parseInt(prixMinField.getText());
              //  int prixMax = Integer.parseInt(prixMaxField.getText());
                //List<Produit> produits = filtrerProduitsParPlageDePrix(prixMin, prixMax);
              //  afficherProduits(produits, root);
          //  }
        });

        topBar.getChildren().addAll(choixRechercheComboBox, rechercheTextField,  rechercherButton);
        root.setTop(topBar);

        ScrollPane scrollPane = new ScrollPane();
        FlowPane produitsPane = new FlowPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        produitsPane.setAlignment(Pos.TOP_CENTER);
        produitsPane.setPadding(new Insets(10));
        produitsPane.setHgap(20);
        produitsPane.setVgap(20);

        scrollPane.setContent(produitsPane);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/CSSmoduleProduit/VitrineClient.css").toExternalForm());

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        produits = consulterProduits();
        panier = new ArrayList<>(); // Initialiser le panier

        afficherProduits(produits, root);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private List<Produit> consulterProduits() {
        List<Produit> produits = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM produit WHERE quantite > 0")) {

            while (resultSet.next()) {
                Produit produit = new Produit();
                produit.setIdProduit(resultSet.getInt("idProduit"));
                produit.setNom(resultSet.getString("nom"));
                produit.setDescription(resultSet.getString("description"));
                produit.setPrix(resultSet.getDouble("prix"));
                produit.setType(resultSet.getString("type"));
                produit.setQuantite(resultSet.getInt("quantite"));
                produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));
                produit.setQuantiteDisponible(resultSet.getInt("quantite") - resultSet.getInt("quantiteVendues"));
                produit.setImage(resultSet.getBytes("image"));

                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }


    private List<Produit> filtrerProduitsParPlageDePrix(int prixMin, int prixMax) {
        List<Produit> produits = new ArrayList<>();

        String query = "SELECT * FROM produit WHERE prix BETWEEN ? AND ? AND quantite > 0";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, prixMin);
            statement.setInt(2, prixMax);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getInt("idProduit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getInt("prix"));
                    produit.setType(resultSet.getString("type"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));
                    produit.setQuantiteDisponible(resultSet.getInt("quantite") - resultSet.getInt("quantiteVendues"));
                    produit.setImage(resultSet.getBytes("image"));
                    produits.add(produit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }


    private List<Produit> rechercherProduits(String typeRecherche) {
        List<Produit> produitsRecherches = new ArrayList<>();

        String query = "SELECT * FROM produit WHERE type LIKE ? AND quantite > 0";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + typeRecherche + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getInt("idProduit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setType(resultSet.getString("type"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));
                    produit.setQuantiteDisponible(resultSet.getInt("quantite") - resultSet.getInt("quantiteVendues"));
                    produit.setImage(resultSet.getBytes("image"));

                    produitsRecherches.add(produit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produitsRecherches;
    }


    private void afficherProduits(List<Produit> produits, BorderPane root) {
        FlowPane produitsPane = new FlowPane();
        produitsPane.setAlignment(Pos.TOP_CENTER);
        produitsPane.setPadding(new Insets(10));
        produitsPane.setHgap(20);
        produitsPane.setVgap(20);

        for (Produit produit : produits) {
            VBox card = createProductCard(produit);
            produitsPane.getChildren().add(card);
        }

        ((ScrollPane) root.getCenter()).setContent(produitsPane);
    }

    private VBox createProductCard(Produit produit) {
        VBox card = new VBox();
        card.getStyleClass().add("product-card");
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(10);
        card.setPrefWidth(220); // Définir une largeur préférée fixe
        card.setMinHeight(350); // Définir une hauteur minimale fixe

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(produit.getImage())));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Label nomLabel = new Label("Nom: " + produit.getNom());
        nomLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label prixLabel = new Label("Prix: " + produit.getPrix() + " Dt");
        prixLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        card.getChildren().addAll(imageView, nomLabel, prixLabel);

        Button addToCartButton = new Button("Ajouter au panier");
        addToCartButton.getStyleClass().add("add-to-cart-button");

        ObservableList<Integer> quantiteOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        ComboBox<Integer> quantiteComboBox = new ComboBox<>(quantiteOptions);
        quantiteComboBox.getStyleClass().add("quantity-combo-box");
        quantiteComboBox.setValue(1);

        addToCartButton.setOnAction(e -> {
            int quantiteChoisie = quantiteComboBox.getValue();
            if (quantiteChoisie <= produit.getQuantite()) {
                ajouterAuPanier(produit, quantiteChoisie);
                showAlert("Succès", "Produit ajouté au panier !");
            } else {
                showAlert("Rupture de stock", "La quantité demandée n'est pas disponible en stock.");
            }
        });

        HBox addToCartBox = new HBox(addToCartButton, quantiteComboBox);
        addToCartBox.setAlignment(Pos.CENTER);
        addToCartBox.setSpacing(10);
        card.getChildren().add(addToCartBox);

        return card;
    }




    private void ajouterAuPanier(Produit produit, int quantite) {
        Commande commande = new Commande(produit.getIdProduit(), produit.getNom(), quantite, produit.getPrix() * quantite);
        panier.add(commande);
    }

    private void consulterPanier() {
        if (panier.isEmpty()) {
            showAlert("Info", "Le panier est vide.");
        } else {
            afficherPanier();
        }
    }

    private void afficherPanier() {
        Stage panierStage = new Stage();
        panierStage.setTitle("Consulter le Panier");

        VBox panierLayout = new VBox();
        panierLayout.setSpacing(10);
        panierLayout.setPadding(new Insets(10));

        for (Commande commande : panier) {
            Label produitLabel = new Label("Produit: " + commande.getNomProduit());
            Label quantiteLabel = new Label("Quantité: " + commande.getQuantite());
            Label montantLabel = new Label("Montant: " + commande.getMontantTotale() + " $");
            Separator separator = new Separator();

            panierLayout.getChildren().addAll(produitLabel, quantiteLabel, montantLabel, separator);
        }

        Button confirmerAchatButton = new Button("Confirmer l'achat");
        Button annulerAchatButton = new Button("Annuler l'achat");

        confirmerAchatButton.setOnAction(e -> confirmerAchat());
        annulerAchatButton.setOnAction(e -> annulerAchat());

        HBox buttonsBox = new HBox(confirmerAchatButton, annulerAchatButton);
        buttonsBox.setSpacing(10);

        panierLayout.getChildren().add(buttonsBox);

        Scene panierScene = new Scene(panierLayout, 300, 400);
        panierStage.setScene(panierScene);
        panierStage.show();
    }

    private void confirmerAchat() {
        // Enregistrer la commande dans la base de données
        for (Commande commande : panier) {
            enregistrerCommande(commande);
            mettreAJourQuantiteProduit(commande.getIdProduit(), commande.getQuantite());
        }
        showAlert("Succès", "Achat confirmé !");
        viderPanier();
    }

    private void mettreAJourQuantiteProduit(int idProduit, int quantiteAchete) {
        try {
            String query = "UPDATE produit SET Quantite = Quantite - ?, quantiteVendues = quantiteVendues + ? WHERE idProduit = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, quantiteAchete);
                statement.setInt(2, quantiteAchete);
                statement.setInt(3, idProduit);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void enregistrerCommande(Commande commande) {
        try {
            String query = "INSERT INTO commande (dateCommande, idProduit, nom, montant) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.setInt(2, commande.getIdProduit());
                statement.setString(3, commande.getNomProduit());
                statement.setDouble(4, commande.getMontantTotale());
                statement.executeUpdate();

                System.out.println("Commande enregistrée avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'insertion de la commande : " + e.getMessage());

        }
    }









    private void annulerAchat() {
        showAlert("Info", "Achat annulé.");
        viderPanier();
    }

    private void viderPanier() {
        panier.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
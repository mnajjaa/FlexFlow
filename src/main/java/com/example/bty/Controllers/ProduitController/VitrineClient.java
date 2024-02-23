package com.example.bty.Controllers.ProduitController;

import com.example.bty.Entities.Commande;
import com.example.bty.Entities.Produit;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
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
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;
import java.util.List;

public class VitrineClient extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pidevgym";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private List<Produit> produits; // Liste des produits à afficher
    private Connection connection;

    private Stage panierStage;

    private List<Commande> panier; // Panier du client

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Affichage des produits");



        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        VBox topBar = new VBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        //topBar.getStyleClass().add("top-bar");
        topBar.setStyle(" -fx-background-color: #2c3e50;\n" +
                "    -fx-padding: 15;\n" +
                "    -fx-border-radius: 15px;" +
                " -fx-background-insets: 0 0 0 218;");
        topBar.setSpacing(5);

        Image panierImage = new Image(getClass().getResourceAsStream("/com/example/bty/imagesModuleProduit/panier.png"));
        ImageView consulterPanierButton = new ImageView(panierImage);
        consulterPanierButton.setOnMouseClicked(event -> consulterPanier());
        consulterPanierButton.setFitWidth(40);  // Ajustez la largeur de l'icône selon vos besoins
        consulterPanierButton.setFitHeight(40);

        TextField rechercheTextField = new TextField();
        rechercheTextField.setPromptText("Rechercher par nom");
        rechercheTextField.getStyleClass().add("search-text-field");

        Button rechercherButton = new Button("Rechercher");
        rechercherButton.getStyleClass().add("search-button");
        rechercherButton.setOnAction(e -> {
            String typeRecherche = rechercheTextField.getText();
            List<Produit> produitsRecherches = rechercherProduits(typeRecherche);
            afficherProduits(produitsRecherches, root);
        });

        HBox searchBox = new HBox(rechercheTextField, rechercherButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);

        HBox buttonContainer = new HBox(consulterPanierButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        topBar.getChildren().addAll(buttonContainer, searchBox);
        root.setTop(topBar);
       // root.setTop(topBar);

        ScrollPane scrollPane = new ScrollPane();
        FlowPane produitsPane = new FlowPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        produitsPane.setAlignment(Pos.TOP_CENTER);
        produitsPane.setPadding(new Insets(20));
        produitsPane.setHgap(30);
        produitsPane.setVgap(30);

        AnchorPane leftDashboard = createLeftDashboard();

        root.setLeft(leftDashboard);



        scrollPane.setContent(produitsPane);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root,800,600);
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/CSSmoduleProduit/VitrineClient.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/dashboardDesign.css").toExternalForm());
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


    private AnchorPane createLeftDashboard() {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(1100, 600);

        AnchorPane anchorPane = new AnchorPane();
        stackPane.getChildren().add(anchorPane);


        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1100, 600);
        borderPane.getStyleClass().add("border-pane");

        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);

        AnchorPane leftAnchorPane = new AnchorPane();
        //leftAnchorPane.setTranslateY(-80);
        leftAnchorPane.setTranslateX(-10);
        leftAnchorPane.setPrefSize(270, 700);
        AnchorPane.setTopAnchor(leftAnchorPane, 0.0);
        AnchorPane.setLeftAnchor(leftAnchorPane, 0.0);
        leftAnchorPane.setTranslateY(-112);
        AnchorPane innerAnchorPane = new AnchorPane();
        innerAnchorPane.getStyleClass().addAll("nav", "nav-border");
        innerAnchorPane.setPrefSize(229, 750);

        FontAwesomeIconView userIcon = new FontAwesomeIconView();
        userIcon.setFill(javafx.scene.paint.Color.WHITE);
        userIcon.setGlyphName("USER");
        userIcon.setLayoutX(82);
        userIcon.setLayoutY(91);
        userIcon.setSize("6em");

        Label welcomeLabel = new Label("Welcome,");
        welcomeLabel.setLayoutX(78);
        welcomeLabel.setLayoutY(101);
        welcomeLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        welcomeLabel.setFont(new javafx.scene.text.Font("Tahoma", 15));

        Label usernameLabel = new Label("MarcoMan");
        usernameLabel.setId("username");
        usernameLabel.setAlignment(javafx.geometry.Pos.CENTER);
        usernameLabel.setLayoutX(11);
        usernameLabel.setLayoutY(120);
        usernameLabel.setPrefSize(201, 23);
        usernameLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        usernameLabel.setFont(new javafx.scene.text.Font("Arial Bold", 20));

        Line line = new Line();
        line.setEndX(100);
        line.setLayoutX(111);
        line.setLayoutY(152);
        line.setStartX(-100);
        line.setStroke(javafx.scene.paint.Color.WHITE);




        Button dashboardBtn = new Button("Dashboard");
        dashboardBtn.setId("dashboard_btn");
        dashboardBtn.setLayoutX(21);
        dashboardBtn.setLayoutY(170);
        dashboardBtn.setMnemonicParsing(false);
        dashboardBtn.setPrefSize(180, 35);
        dashboardBtn.getStyleClass().addAll("nav-btn", "dashboard-btn");
        dashboardBtn.setText("Dashboard");

        Button userBtn = new Button("Utilisateurs");
        userBtn.setId("user_btn");
        userBtn.setLayoutX(21);
        userBtn.setLayoutY(220);
        userBtn.setMnemonicParsing(false);
        userBtn.setPrefSize(180, 35);
        userBtn.getStyleClass().addAll("nav-btn");

        Button coursBtn = new Button("Cours");
        coursBtn.setId("cours_btn");
        coursBtn.setLayoutX(21);
        coursBtn.setLayoutY(270);
        coursBtn.setMnemonicParsing(false);
        coursBtn.setPrefSize(180, 35);
        coursBtn.getStyleClass().addAll("nav-btn");

        Button evenementBtn = new Button("Evenement");
        evenementBtn.setId("evenement_btn");
        evenementBtn.setLayoutX(21);
        evenementBtn.setLayoutY(320);
        evenementBtn.setMnemonicParsing(false);
        evenementBtn.setPrefSize(180, 35);
        evenementBtn.getStyleClass().addAll("nav-btn");

        Button produitBtn = new Button("Produits");
        produitBtn.setId("produit_btn");
        produitBtn.setLayoutX(21);
        produitBtn.setLayoutY(370);
        produitBtn.setMnemonicParsing(false);
        produitBtn.setPrefSize(180, 35);
        produitBtn.getStyleClass().addAll("nav-btn");

        Button coachBtn = new Button("Coaching privé");
        coachBtn.setId("coach_btn");
        coachBtn.setLayoutX(21);
        coachBtn.setLayoutY(420);
        coachBtn.setMnemonicParsing(false);
        coachBtn.setPrefSize(180, 35);
        coachBtn.getStyleClass().addAll("nav-btn");

        Button reclamationBtn = new Button("Réclamation");
        reclamationBtn.setId("reclamation_btn");
        reclamationBtn.setLayoutX(21);
        reclamationBtn.setLayoutY(470);
        reclamationBtn.setMnemonicParsing(false);
        reclamationBtn.setPrefSize(180, 35);
        reclamationBtn.getStyleClass().addAll("nav-btn");

        Button logoutBtn = new Button();
        logoutBtn.setId("logout");
        logoutBtn.setLayoutX(14);
        logoutBtn.setLayoutY(545);
        logoutBtn.setMnemonicParsing(false);
        logoutBtn.getStyleClass().addAll("logout", "shadow");


        FontAwesomeIconView logoutIcon = new FontAwesomeIconView();
        logoutIcon.setFill(javafx.scene.paint.Color.WHITE);
        logoutIcon.setGlyphName("SIGN_OUT");
        logoutIcon.setSize("2em");

        logoutBtn.setGraphic(logoutIcon);

        Label logoutLabel = new Label("Logout");
        logoutLabel.setLayoutX(58);
        logoutLabel.setLayoutY(551);
        logoutLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        logoutLabel.setFont(new javafx.scene.text.Font(15));

        innerAnchorPane.getChildren().addAll(userIcon, welcomeLabel, usernameLabel, line, dashboardBtn, userBtn, coursBtn, evenementBtn, produitBtn, coachBtn, reclamationBtn, logoutBtn, logoutLabel);

        leftAnchorPane.getChildren().add(innerAnchorPane);

        borderPane.setLeft(leftAnchorPane);
        anchorPane.getChildren().add(borderPane);
        return leftAnchorPane;
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

    private List<Produit> rechercherProduits(String typeRecherche) {
        List<Produit> produitsRecherches = new ArrayList<>();

        String query = "SELECT * FROM produit WHERE nom LIKE ? AND quantite > 0";
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
        card.setSpacing(6);
        card.setPrefWidth(200); // Définir une largeur préférée fixe
        card.setMinHeight(330); // Définir une hauteur minimale fixe

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(produit.getImage())));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Label nomLabel = new Label("Nom: " + produit.getNom());
        nomLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;-fx-text-fill: #1E0F1C");
        Label typeLabel = new Label("Type: " + produit.getNom());
        typeLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;-fx-text-fill: #2c3e50;");
        Label prixLabel = new Label("Prix: " + produit.getPrix() + " Dt");
        prixLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #A62609;");

        card.getChildren().addAll(imageView, nomLabel,typeLabel, prixLabel);

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
        panierStage = new Stage();
        panierStage.setTitle("Consulter le Panier");

        VBox panierLayout = new VBox();
        panierLayout.setSpacing(10);
        panierLayout.setPadding(new Insets(10));

        for (Commande commande : panier) {
            Label produitLabel = new Label("Produit: " + commande.getNomProduit());
            Label quantiteLabel = new Label("Quantité: " + commande.getQuantite());
            Label montantLabel = new Label("Montant: " + commande.getMontantTotale() + " Dt");
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

        // Demander si l'utilisateur veut imprimer la facture
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Impression de la facture");
        confirmDialog.setHeaderText("Voulez-vous imprimer votre facture ?");
        confirmDialog.setContentText("Choisissez votre option :");

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");

        confirmDialog.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        confirmDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeOui) {
                // L'utilisateur a choisi d'imprimer la facture
                imprimerFacture(panier);

                // Afficher une alerte
                showAlert("Succès", "Facture téléchargée avec succès !");

                // Fermer la fenêtre du panier
                if (panierStage != null) {
                    panierStage.hide();
                }

                // Vider le panier
                viderPanier();
            } else {
                // L'utilisateur a choisi de ne pas imprimer la facture
                if (panierStage != null) {
                    panierStage.hide();
                    showAlert("Succès", "Achat confirmé");
                }

                // Vider le panier
                viderPanier();
            }
        });
    }


    private void imprimerFacture(List<Commande> panier) {
        Document document = new Document();

        try {
            // Utiliser la date et l'heure actuelles comme identifiant unique
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            String codeAchat1 = generateUniqueCode1();
            String codeAchat = generateUniqueCode();



            String nomFichier = "facture_" + codeAchat + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            document.open();

            // Ajouter en-tête
            Font fontEnTete = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph enTete = new Paragraph("Facture d'achat - " + timestamp + " (Code d'achat: " + codeAchat1 + ")", fontEnTete);
            enTete.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(enTete);

            // Ajouter table des détails de la commande
            PdfPTable table = new PdfPTable(3); // 3 colonnes pour Produit, Quantité et Montant
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);

            Font fontDetails = new Font(Font.FontFamily.HELVETICA, 12);

            PdfPCell cellProduit = new PdfPCell(new Phrase("Produit", fontDetails));
            PdfPCell cellQuantite = new PdfPCell(new Phrase("Quantité", fontDetails));
            PdfPCell cellMontant = new PdfPCell(new Phrase("Montant (Dt)", fontDetails));

            table.addCell(cellProduit);
            table.addCell(cellQuantite);
            table.addCell(cellMontant);

            for (Commande commande : panier) {
                table.addCell(commande.getNomProduit());
                table.addCell(String.valueOf(commande.getQuantite()));
                table.addCell(String.valueOf(commande.getMontantTotale()));
            }

            document.add(table);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private String generateUniqueCode() {
        // Générer un code unique en utilisant la date et un identifiant aléatoire
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String datePart = dateFormat.format(new Date());

        // Ajouter un identifiant aléatoire (par exemple, 4 chiffres)
        String randomPart = String.format("%05d", new Random().nextInt(10000));

        return datePart + randomPart;
    }

    private String generateUniqueCode1() {
        // Générer un code unique en utilisant la date et un identifiant aléatoire
       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
       // String datePart = dateFormat.format(new Date());

        // Ajouter un identifiant aléatoire (par exemple, 4 chiffres)
        String randomPart = String.format("%05d", new Random().nextInt(10000));

        return  randomPart;
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

        if (panierStage != null) {
            viderPanier();
            panierStage.hide();
        }
    }

    private void viderPanier() {
        System.out.println("Vidage du panier...");
        panier.clear();
        //afficherPanier();
        System.out.println("Panier vidé.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
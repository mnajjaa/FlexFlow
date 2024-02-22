package com.example.bty.Controllers.ProduitController;

import com.example.bty.Entities.Produit;
import com.example.bty.Services.ServiceProduit;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Ajoutproduit extends Application {

    private TextField idField;
    private TextField nomField;
    private TextField descriptionField;
    private TextField prixField;
    private TextField typeField;
    private TextField quantiteField;
    private TextField quantiteVenduesField;

    private Button uploadButton;
    private Button envoyerButton;

    private ImageView uploadedImageView;
    private ComboBox<String> typeComboBox;

    private File selectedImage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formulaire d'ajout de produit");

        // Card Container
        VBox cardContainer = new VBox();
        cardContainer.getStyleClass().add("card-container");
        cardContainer.setPadding(new Insets(20));
        cardContainer.setSpacing(10);

        // Card Title
        Label cardTitle = new Label("Ajouter un produit");
        cardTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #DDE2C6; -fx-font-family: 'Arial', sans-serif;");


        // Form Grid
        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setStyle("-fx-background-color: #FFFAFA; -fx-padding: 25; -fx-border-radius: 10; -fx-background-radius: 10; -fx-alignment: center;");


        // Labels and TextFields
        Label idLabel = new Label("Code produit :");
        idField = new TextField();
        idField.getStyleClass().add("text-field");
        gridPane.addRow(0, idLabel, idField);

        Label nomLabel = new Label("Nom :");
        nomField = new TextField();
        gridPane.addRow(1, nomLabel, nomField);

        Label descriptionLabel = new Label("Description :");
        descriptionField = new TextField();
        gridPane.addRow(2, descriptionLabel, descriptionField);

        Label prixLabel = new Label("Prix :");
        prixField = new TextField();
        gridPane.addRow(3, prixLabel, prixField);

        Label typeLabel = new Label("Type :");
        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Vitamine", "Accessoires", "Protéine","Vetement");
        gridPane.addRow(4, typeLabel, typeComboBox);


        Label quantiteLabel = new Label("Quantité :");
        quantiteField = new TextField();
        gridPane.addRow(5, quantiteLabel, quantiteField);

        Label quantiteVenduesLabel = new Label("Quantité Vendues :");
        quantiteVenduesField = new TextField("0"); // Initialisation avec la valeur par défaut
        gridPane.addRow(6, quantiteVenduesLabel, quantiteVenduesField);


        uploadedImageView = new ImageView(); // ImageView pour afficher l'image chargée

        uploadButton = new Button("Upload Image");
        uploadButton.getStyleClass().add("upload-button");
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner une image");
            selectedImage = fileChooser.showOpenDialog(primaryStage);

            if (selectedImage != null) {
                try {
                    FileInputStream input = new FileInputStream(selectedImage);
                    Image image = new Image(input);
                    uploadedImageView.setImage(image);  // Mettez à jour l'ImageView
                    uploadedImageView.setFitWidth(60);
                    uploadedImageView.setFitHeight(60);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        HBox uploadBox = new HBox(10, uploadButton, uploadedImageView); // HBox pour le bouton "Upload" et l'image chargée
        gridPane.add(uploadBox, 0, 7);


        envoyerButton = new Button("Envoyer");
        envoyerButton.getStyleClass().add("envoyer-button");
        envoyerButton.setOnAction(e -> ajouterProduit());
        gridPane.add(envoyerButton, 1, 9);

        // Adding elements to the VBox (Card Container)
        cardContainer.getChildren().addAll(cardTitle, gridPane);
       // VBox.setMargin(cardContainer, new Insets(50, 0, 50, 20));
        Scene scene = new Scene(cardContainer, 800, 620);
        scene.getStylesheets().add(getClass().getResource("/com/example/bty/CSSmoduleProduit/Ajoutproduit.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void ajouterProduit() {
        try {
            // Vérifier si tous les champs sont remplis
            if (idField.getText().isEmpty() || nomField.getText().isEmpty() || descriptionField.getText().isEmpty()
                    || prixField.getText().isEmpty() || typeComboBox.getValue() == null
                    || quantiteField.getText().isEmpty() || quantiteVenduesField.getText().isEmpty()) {
                afficherMessage("Erreur de saisie", "Veuillez remplir tous les champs.");
                return;
            }

            int id = Integer.parseInt(idField.getText());
            String nom = nomField.getText();
            String description = descriptionField.getText();
            double prix = Double.parseDouble(prixField.getText());
            String type = typeComboBox.getValue();
            int quantite = Integer.parseInt(quantiteField.getText());
            int quantiteVendues = Integer.parseInt(quantiteVenduesField.getText());


            byte[] imageBytes = null;
            if (selectedImage != null) {
                try (FileInputStream fis = new FileInputStream(selectedImage)) {
                    imageBytes = new byte[(int) selectedImage.length()];
                    fis.read(imageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Produit produit = new Produit(id, nom, description, prix, type, quantite, quantiteVendues, imageBytes);
            ServiceProduit serviceProduit = new ServiceProduit();
            if (serviceProduit.ajouterProduit(produit)) {
                afficherMessage("Succès", "Le produit a été ajouté avec succès.");
            } else {
                afficherMessage("Échec", "L'ajout du produit a échoué.");
            }
            // Réinitialiser les champs après l'ajout
            idField.clear();
            nomField.clear();
            descriptionField.clear();
            prixField.clear();
            typeComboBox.getSelectionModel().clearSelection();
            quantiteField.clear();
            quantiteVenduesField.clear();
            uploadedImageView.setImage(null);
            selectedImage = null;

        } catch (NumberFormatException e) {
            afficherMessage("Erreur de saisie", "Veuillez saisir des valeurs numériques valides pour les champs numériques (ID, Prix, Quantité).");
        }
    }

    private void afficherMessage(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private boolean estChaineValide(String chaine) {
        // Vérifie si la chaîne ne contient pas de chiffres
        return chaine.matches("\\D*");
    }
}

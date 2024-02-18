package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Formoffre extends Application {

    private static Connection connexion;
    private PreparedStatement pst;

    private File selectedImage;
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formulaire d'ajout d'offre");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Label et champ de texte pour l'ID
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);

        // Label et choix de spécialité
        Label specialiteLabel = new Label("Spécialité:");
        ChoiceBox<String> specialiteChoice = new ChoiceBox<>();
        specialiteChoice.getItems().addAll("Musculation", "Cardio", "Yoga", "Boxe");
        specialiteChoice.setValue("Musculation"); // Spécialité par défaut
        grid.add(specialiteLabel, 0, 1);
        grid.add(specialiteChoice, 1, 1);

        // Label et champ de texte pour le tarif par heure
        Label tarifLabel = new Label("Tarif par heure:");
        TextField tarifField = new TextField();
        grid.add(tarifLabel, 0, 2);
        grid.add(tarifField, 1, 2);

        // Label et champ de texte pour le coach
        Label coachLabel = new Label("Coach:");
        TextField coachField = new TextField();
        grid.add(coachLabel, 0, 3);
        grid.add(coachField, 1, 3);

        // Bouton pour charger une image
        Button uploadButton = new Button("Choisir une photo");
        imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        grid.add(uploadButton, 0, 4);
        grid.add(imageView, 1, 4);

        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une photo");
            selectedImage = fileChooser.showOpenDialog(primaryStage);
            if (selectedImage != null) {
                try {
                    Image image = new Image(new FileInputStream(selectedImage));
                    imageView.setImage(image);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Bouton d'ajout de l'offre
        Button addButton = new Button("Ajouter");
        grid.add(addButton, 1, 5);

        addButton.setOnAction(e -> {
            String id = idField.getText();
            String specialite = specialiteChoice.getValue();
            double tarif = Double.parseDouble(tarifField.getText());
            String coach = coachField.getText();

            // Ici, vous pouvez ajouter la logique pour sauvegarder l'offre dans la base de données

            System.out.println("ID: " + id);
            System.out.println("Spécialité: " + specialite);
            System.out.println("Tarif par heure: " + tarif);
            System.out.println("Coach: " + coach);
            System.out.println("Image: " + selectedImage.getAbsolutePath()); // Chemin de l'image
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

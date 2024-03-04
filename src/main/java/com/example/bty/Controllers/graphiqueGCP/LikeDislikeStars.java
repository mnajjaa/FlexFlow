package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class LikeDislikeStars extends Application {

    private int starsSelected = 0;
    private final int maxStars = 5;
    private Label selectionLabel;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Label to display the current selection
        selectionLabel = new Label("Selection: " + starsSelected + "/" + maxStars);

        // Create star polygons
        Polygon[] stars = new Polygon[maxStars];
        for (int i = 0; i < maxStars; i++) {
            stars[i] = createStar();
            int index = i;
            stars[i].setOnMouseClicked(e -> {
                starsSelected = index + 1;
                updateSelectionLabel();
                updateStarColors(stars, starsSelected);
            });
        }

        HBox starBox = new HBox(5);
        starBox.getChildren().addAll(stars);

        // Button to submit the selection
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Do something with the selected stars
            System.out.println("User selected " + starsSelected + " stars.");
            // Reset the selection
            starsSelected = 0;
            updateSelectionLabel();
            updateStarColors(stars, starsSelected);
        });

        root.getChildren().addAll(selectionLabel, starBox, submitButton);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Like & Dislike with Colored Stars");
        primaryStage.show();
    }

    // Method to create a star polygon
    private Polygon createStar() {
        Polygon star = new Polygon();
        star.getPoints().addAll(
                0.0, 0.0,
                10.0, 30.0,
                40.0, 40.0,
                20.0, 70.0,
                30.0, 100.0,
                0.0, 80.0,
                -30.0, 100.0,
                -20.0, 70.0,
                -40.0, 40.0,
                -10.0, 30.0
        );
        star.setFill(Color.WHITE);
        return star;
    }

    // Method to update the selection label
    private void updateSelectionLabel() {
        selectionLabel.setText("Selection: " + starsSelected + "/" + maxStars);
    }

    // Method to update the colors of the stars based on the selection
    private void updateStarColors(Polygon[] stars, int selectedStars) {
        for (int i = 0; i < maxStars; i++) {
            if (i < selectedStars) {
                stars[i].setFill(Color.YELLOW); // Colored star
            } else {
                stars[i].setFill(Color.WHITE); // White star
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

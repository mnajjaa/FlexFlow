package com.example.bty.Controllers.graphiqueGCP;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InterfaceOffre extends Application {
    private ImageView backgroundImag;

    @Override
    public void start(Stage primaryStage) {
        Image image = null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\farah\\IdeaProjects\\FlexFlow\\src\\main\\Resources\\images\\aa.jpg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.backgroundImag = new ImageView(image);
        backgroundImag.setFitHeight(600);
        backgroundImag.setFitWidth(700);

        Button consulterButton = new Button("Consulter liste des offres");
        consulterButton.setOnAction(event -> {
            consulterOffres();
        });
        // Création du titre
        Label titleLabel = new Label("FlexFlow");
        titleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-padding: 100px 0  0 0 2px;");

        HBox titleBox = new HBox();
        titleBox.getChildren().add(titleLabel);
        titleBox.setAlignment(Pos.CENTER);


        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleBox, consulterButton);
        vbox.setStyle("-fx-padding: 160px;");
        VBox.setVgrow(titleBox, javafx.scene.layout.Priority.ALWAYS);
        vbox.setAlignment(Pos.CENTER); // Alignement du VBo

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImag, vbox);
        StackPane.setAlignment(vbox, Pos.CENTER_RIGHT); // Alignement à droite

        Scene scene = new Scene(stackPane, 700, 650);
        scene.getStylesheets().add(getClass().getResource("/Styles/StyleAR.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void consulterOffres() {
        ConsultationOffre1 consultationOffre = new ConsultationOffre1();
        consultationOffre.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
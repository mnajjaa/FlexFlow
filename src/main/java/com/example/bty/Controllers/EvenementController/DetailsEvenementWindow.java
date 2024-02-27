package com.example.bty.Controllers.EvenementController;

import com.example.bty.Entities.Evenement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class DetailsEvenementWindow extends Stage {

//    public VBox DetailsEvenementWindow(Evenement evenement) {
//        VBox card = new VBox();
//        card.getStyleClass().add("product-card");
//        card.setAlignment(Pos.TOP_CENTER);
//        card.setSpacing(10);
//        card.setPrefWidth(220); // Définir une largeur préférée fixe
//        card.setMinHeight(250);
//        setTitle("Détails de l'événement");
//        initModality(Modality.APPLICATION_MODAL);
//        setResizable(false);
//
//        VBox detailsBox = new VBox();
//        detailsBox.setSpacing(10);
//        detailsBox.setPadding(new Insets(20));
//
//        Label nomLabel = new Label("Nom de l'événement : " + evenement.getNom());
//        Label dateLabel = new Label("Date : " + evenement.getDate());
//        Label timeLabel = new Label("Time : " + evenement.getTime());
//        Label categorieLabel = new Label("Catégorie : " + evenement.getCategorie());
//        Label objectifLabel = new Label("Objectif : " + evenement.getObjectif());
//        Label placeLabel = new Label("Nombre de places : " + evenement.getNbre_place());
//        Button SeeButton = new Button("Reserver");
//        SeeButton.getStyleClass().add("add-to-cart-button");
//        detailsBox.getChildren().addAll(nomLabel, dateLabel, timeLabel, categorieLabel, objectifLabel, placeLabel);
//        card.getChildren().addAll( nomLabel, dateLabel, timeLabel, categorieLabel, objectifLabel, placeLabel);
//
//
//        Scene scene = new Scene(detailsBox, 400, 300);
//        scene.getStylesheets().add(getClass().getResource("/com/example/bty/CSSmoduleEvenement/VitrineClient.css").toExternalForm());
//
//        setScene(scene);
//        return card;
//    }
private static Map<Evenement, VBox> detailsMap = new HashMap<>();


    public static void display(Evenement evenement) {
    Stage detailsStage = new Stage();
    detailsStage.setTitle("Détails de l'événement");
    detailsStage.initModality(Modality.APPLICATION_MODAL);
    detailsStage.setResizable(false);
    VBox detailsCard = createDetailsCard(evenement);

    Scene scene = new Scene(detailsCard, 400, 300);
    scene.getStylesheets().add(DetailsEvenementWindow.class.getResource("/com/example/bty/CSSmoduleEvenement/VitrineClient.css").toExternalForm());

    detailsStage.setScene(scene);
    detailsStage.show();
}
public static VBox createDetailsCard(Evenement evenement) {
    VBox detailsCard = detailsMap.get(evenement);

    Stage detailsStage = new Stage();
    if (detailsCard == null) {

        detailsCard = new VBox();
        detailsCard.getStyleClass().add("product-card");
        detailsCard.setSpacing(10);
        detailsCard.setPadding(new Insets(20));


        Label dateLabel = new Label("Date : " + evenement.getDate());
        Label timeLabel = new Label("Time : " + evenement.getTime());
        Label categorieLabel = new Label("Catégorie : " + evenement.getCategorie());
        Label objectifLabel = new Label("Objectif : " + evenement.getObjectif());
        Label placeLabel = new Label("Nombre de places : " + evenement.getNbre_place());
        Button ReserverButton = new Button("Reserver");
        ReserverButton.getStyleClass().add("add-to-cart-button");

        detailsCard.getChildren().addAll( dateLabel, timeLabel, categorieLabel, objectifLabel, placeLabel, ReserverButton);
        detailsMap.put(evenement, detailsCard);
    }
    return detailsCard;
}

}

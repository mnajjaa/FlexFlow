package com.example.bty.Controllers.ReponseController;
import com.example.bty.Entities.Reponse;
import com.example.bty.Services.ServiceReponse;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AfficherReponse extends Application {

    private ServiceReponse reponseService = new ServiceReponse();

    @Override
    public void start(Stage primaryStage) {
        TableView<Reponse> tv_reponse = new TableView<>();
        TableColumn<Reponse, Integer> col_reponse_id = new TableColumn<>("ID Réponse");
        TableColumn<Reponse, String> col_reponse = new TableColumn<>("Réponse");
        TableColumn<Reponse, Integer> col_reclamation_id = new TableColumn<>("ID Réclamation");
        TableColumn<Reponse, Date> col_date_reclamation = new TableColumn<>("Date Réclamation");
        TableColumn<Reponse, String> col_titre_reclamation = new TableColumn<>("Titre");
        TableColumn<Reponse, String> col_descritpion_reclamation = new TableColumn<>("Description");
        TableColumn<Reponse, String> col_etat_reclamation = new TableColumn<>("État");

        tv_reponse.getColumns().addAll(col_reponse_id, col_reponse, col_reclamation_id, col_date_reclamation, col_titre_reclamation, col_descritpion_reclamation, col_etat_reclamation);

        ObservableList<Reponse> observableList = FXCollections.observableList(reponseService.readallreponsev2());
        tv_reponse.setItems(observableList);

        col_reponse_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_reponse.setCellValueFactory(new PropertyValueFactory<>("reponse_reclamation"));
        col_reclamation_id.setCellValueFactory(new PropertyValueFactory<>("id"));
//        col_date_reclamation.setCellValueFactory(cellData -> new PropertyValueFactory<>("reclamation"));
//        col_titre_reclamation.setCellValueFactory(cellData -> new PropertyValueFactory<>("reclamation"));
//        col_descritpion_reclamation.setCellValueFactory(cellData -> new PropertyValueFactory<>("reclamation"));
//        col_etat_reclamation.setCellValueFactory(cellData -> new PropertyValueFactory<>("reclamation"));

        col_date_reclamation.setCellValueFactory(cellData -> {
            Date dateReclamation = cellData.getValue().getReclamation().getDate_reclamation();
            return new SimpleObjectProperty<>(dateReclamation);
        });

        col_titre_reclamation.setCellValueFactory(cellData -> {
            String titreReclamation = cellData.getValue().getReclamation().getTitre_reclamation();
            return new SimpleStringProperty(titreReclamation);
        });

        col_descritpion_reclamation.setCellValueFactory(cellData -> {
            String descriptionReclamation = cellData.getValue().getReclamation().getDescription();
            return new SimpleStringProperty(descriptionReclamation);
        });

        col_etat_reclamation.setCellValueFactory(cellData -> {
            String etatReclamation = cellData.getValue().getReclamation().getEtat();
            return new SimpleStringProperty(etatReclamation);
        });


        col_date_reclamation.setCellFactory(column -> new TableCell<Reponse, Date>() {
            private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        });

        AnchorPane root = new AnchorPane(tv_reponse);
        Scene scene = new Scene(root, 930, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Afficher Réponse");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



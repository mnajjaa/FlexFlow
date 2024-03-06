package com.example.bty.Controllers.ReponseController;
import com.example.bty.Entities.Reponse;
import com.example.bty.Services.ServiceReponse;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class AfficherReponseController {
    ServiceReponse reponseService = new ServiceReponse();

    @FXML
    private TableColumn<Reponse, Integer> col_reponse_id;

    @FXML
    private TableColumn<Reponse, String> col_reponse;

    @FXML
    private TableColumn<Reponse, Integer> col_reclamation_id;

    @FXML
    private TableColumn<Reponse, Date> col_date_reclamation;

    @FXML
    private TableColumn<Reponse, String> col_titre_reclamation;

    @FXML
    private TableColumn<Reponse, String> col_descritpion_reclamation;

    @FXML
    private TableColumn<Reponse, String> col_etat_reclamation;

    @FXML
    private TableView<Reponse> tv_reponse;


    @FXML
    void AjouterReclamation(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AjouterReclamation.fxml"));
            tv_reponse.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void initialize() {
        ObservableList<Reponse> observableList = FXCollections.observableList(reponseService.readallreponsev2());
        // System.out.println(observableList);

        tv_reponse.setItems(observableList);

        col_reponse_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_reponse.setCellValueFactory(new PropertyValueFactory<>("reponse_reclamation"));
        col_reclamation_id.setCellValueFactory(new PropertyValueFactory<>("id"));
//        col_date_reclamation.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
//        col_titre_reclamation.setCellValueFactory(new PropertyValueFactory<>("titre_reclamation"));
//        col_descritpion_reclamation.setCellValueFactory(new PropertyValueFactory<>("description"));
//        col_etat_reclamation.setCellValueFactory(new PropertyValueFactory<>("etat"));
        //col_date_reclamation.setCellValueFactory(cellData -> new SimpleDateFormat(cellData.getValue().getReclamation().getDate_reclamation()));
        col_date_reclamation.setCellValueFactory(cellData -> {
            Date dateReclamation = cellData.getValue().getReclamation().getDate_reclamation();
            return new SimpleObjectProperty<>(dateReclamation);
        });


        col_titre_reclamation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReclamation().getTitre_reclamation()));
        col_descritpion_reclamation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReclamation().getDescription()));
        col_etat_reclamation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReclamation().getEtat()));
    }
}

package com.example.bty.Controllers.ReponseController;
import com.example.bty.Entities.Reclamation;
import com.example.bty.Entities.Reponse;
import com.example.bty.Services.ServiceReponse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AjouterReponseController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_reponse.setVisible(false);

    }
    ServiceReponse reponseService= new ServiceReponse();

    @FXML
    private Label lb;

    @FXML
    private TextField tf_reponse;



    @FXML
    private TextField tf_id_rec;

    @FXML
    private Label error_reponse;

    private boolean validateReponse() {
//vide et spésiaux
        //        String nomText = tf_reponse.getText();
//
//        if (nomText != null && !nomText.isEmpty()) {
//
//            if (Pattern.matches("^[a-zA-Z0-9]+$", nomText)) {
//                error_reponse.setVisible(false);
//                return true;
//            } else {
//                error_reponse.setVisible(true);
//            }
//        } else {
//            error_reponse.setVisible(true);
//
//
//        }
//        return false;
//    }
        String nomText = tf_reponse.getText();

        if (nomText != null && !nomText.isEmpty()) {
            error_reponse.setVisible(false);
            return true;
        } else {
            error_reponse.setVisible(true);

        }
        return false;
    }

    //    @FXML
//    void AjouterReponse(ActionEvent event) {
//        if (validateReponse()) {
//            reponseService.addReponse(new Reponse(tf_reponse.getText(),Integer.parseInt(tf_id_rec.getText())));
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success");
//            alert.setContentText("Reclamation ajoute");
//            alert.showAndWait();
//        }
//    }
    @FXML
    void AjouterReponse(ActionEvent event) {
        if (validateReponse()) {
            // Récupérer le texte de la réponse
            String texteReponse = tf_reponse.getText();

            try {
                // Récupérer l'ID de la réclamation et le convertir en entier
                int idReclamation = Integer.parseInt(tf_id_rec.getText());

                // Créer une instance de Reclamation avec l'ID
                Reclamation reclamation = new Reclamation(idReclamation);

                // Créer une instance de Reponse avec le texte de la réponse et l'instance de Reclamation
                Reponse reponse = new Reponse(texteReponse, reclamation);

                // Ajouter la réponse en utilisant le service de réponses
                reponseService.addReponse(reponse);

                // Afficher un message d'alerte pour indiquer le succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Réponse ajoutée avec succès");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                // Gérer les erreurs de format de l'ID de réclamation
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("L'ID de réclamation doit être un entier valide");
                alert.showAndWait();
            }
        }
    }







//ajouter reponse avec jointure
//    void AjouterReponse(ActionEvent event) {
//        if (validateReponse()) {
//            int idRec = Integer.parseInt(tf_id_rec());
//            Reclamation reclamation =  getReclamationById(idRec);
//            reponseService.addReponse(new Reponse(tf_reponse.getText(),reclamation));
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success");
//            alert.setContentText("Reclamation ajoute");
//            alert.showAndWait();
//        }
//    }
}



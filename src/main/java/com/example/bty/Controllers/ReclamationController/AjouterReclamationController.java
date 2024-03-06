package com.example.bty.Controllers.ReclamationController;

import com.example.bty.Entities.Reclamation;
import com.example.bty.Services.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AjouterReclamationController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_titre.setVisible(false);
        error_description.setVisible(false);
        error_carctére.setVisible(false);
    }

    ServiceReclamation reclamationService = new ServiceReclamation();



    @FXML
    private TextField tf_description;



    @FXML
    private TextField tf_titre_reclamation;

    @FXML
    private Label error_titre;

    @FXML
    private Label error_description;

    @FXML
    private Label error_carctére;
    private boolean validateTitre() {
        String nomText = tf_titre_reclamation.getText();

        if (nomText != null && !nomText.isEmpty()) {
            error_titre.setVisible(false);
            return true;
        } else {
            error_titre.setVisible(true);

        }
        return false;
    }
    private boolean validateDescription() {
        String nomText = tf_description.getText();

        if (nomText != null && !nomText.isEmpty()) {
            error_description.setVisible(false);
            return true;
        } else {
            error_description.setVisible(true);

        }
        return false;
    }
    private boolean validatecaractére() {
        String nomText = tf_titre_reclamation.getText();

        if (Pattern.matches("^[a-zA-Z0-9]+$", nomText)) {
            error_carctére.setVisible(false);
            return true;
        } else {
            error_carctére.setVisible(true);

        }
        return false;
    }


    @FXML
    void AjouterReclamation(ActionEvent event) {
        // try {
        if (validateTitre() && validateDescription() && validatecaractére()) {
            reclamationService.addReclamationv2(new Reclamation(tf_titre_reclamation.getText(), tf_description.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Reclamation ajoute");
            alert.showAndWait();
      /*  } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }*/
        }
    }

}




package com.example.bty.Controllers.usercontroller;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.MailerService;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class UserProfilController implements Initializable {
    public AnchorPane main_form;
    public AnchorPane dashboard_form;
    public AnchorPane user_profil;
    public AnchorPane box_1;
    @FXML
    public TextField profil_name;
    public TextField profil_email;
    public TextField profil_telephone;
    public Button coaches_updateBtn1;
    public AnchorPane box_11;
    public PasswordField new_password;
    public PasswordField confirm_password;
    public Button profil_update;
    public ImageView profile_img;
    public Button update_img_btn;
    public ImageView image;


    public AnchorPane coaches_form;
    public TextField coaches_name;
    public TextField coaches_email;
    public TextField coaches_telephone;
    public Button coaches_createBtn;
    public Button coaches_updateBtn;
    IServiceUser serviceUser00 = new ServiceUser();
    MailerService mailerService = new MailerService();
    Session session = Session.getInstance();
    User u = session.getLoggedInUser();

    public static String pathImage;
    public StackPane contentPlaceholder;

    User user;

    public void updateImgBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );
        pathImage = fileChooser.showOpenDialog(null).getAbsolutePath();
        Image img = new Image("file:" + pathImage);
        profile_img.setImage(img);
        serviceUser00.updateImage(pathImage, Session.getInstance().getLoggedInUser().getId());
    }

    public void updatePassword(ActionEvent event) {
        if (!Objects.equals(new_password.getText(), confirm_password.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Password");
            alert.setHeaderText(null);
            alert.setContentText("Password not match");
            alert.showAndWait();
        } else {
            serviceUser00.updatePassword(new_password.getText(), Session.getInstance().getLoggedInUser().getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password");
            alert.setHeaderText(null);
            alert.setContentText("Password updated");
            alert.showAndWait();
            new_password.clear();
            confirm_password.clear();
        }
    }

    public void coachesUpdateBtn(ActionEvent actionEvent) {
        User loggedInUser = Session.getInstance().getLoggedInUser();
        System.out.println(profil_name.getText()+" "+profil_email.getText()+" "+profil_email.getText());

        User coach = new User(loggedInUser.getId(), profil_name.getText(), profil_email.getText(), profil_telephone.getText(), true);
        serviceUser00.update(coach);
        //Session.setLoggedInUser(coach);



            loadContent("/UserProfil.fxml");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

SetUser();
    }



    private void loadContent(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent content = loader.load();
            contentPlaceholder.getChildren().clear();
            contentPlaceholder.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement du fichier FXML
        }

    }

    public void SetUser() {
        User loggedInUser = Session.getInstance().getLoggedInUser();
        profil_name.setText(loggedInUser.getName());
        profil_email.setText(loggedInUser.getEmail());
        profil_telephone.setText(loggedInUser.getTelephone());
        image.setImage(new Image("file:" + loggedInUser.getImage()));
        System.out.println(profil_email.getText()+" "+profil_name.getText()+" "+profil_telephone.getText());

    }
}
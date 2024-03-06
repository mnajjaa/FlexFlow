package com.example.bty.Controllers.usercontroller;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.MailerService;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.util.Objects;


public class UserProfilController {
    public AnchorPane main_form;
    public AnchorPane dashboard_form;
    public AnchorPane user_profil;
    public AnchorPane box_1;
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
    IServiceUser serviceUser00=new ServiceUser();
    MailerService mailerService = new MailerService();
    Session session = Session.getInstance();
    User u=session.getLoggedInUser();

    public static String pathImage;

    User user ;
    public void updateImgBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );
        pathImage= fileChooser.showOpenDialog(null).getAbsolutePath();
        Image img = new Image("file:"+pathImage);
        profile_img.setImage(img);
        serviceUser00.updateImage(pathImage, Session.getInstance().getLoggedInUser().getId());
    }
    public void updatePassword(ActionEvent event) {
        if(!Objects.equals(new_password.getText(), confirm_password.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Password");
            alert.setHeaderText(null);
            alert.setContentText("Password not match");
            alert.showAndWait();
        }
        else{
            serviceUser00.updatePassword(new_password.getText(),Session.getInstance().getLoggedInUser().getId());
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

        User coach=new User(loggedInUser.getId(), coaches_name.getText(),coaches_email.getText(),coaches_telephone.getText(),Role.COACH,true,null);
        serviceUser00.update(coach);

       // loadCoachListInterface();



    }
}
package com.example.bty.Controllers;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.LoginView;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceUser;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginGymController implements Initializable {
    public TextField su_telephone;
    public FontAwesomeIconView close_icon;
    @FXML
    private Button si_loginBtn;
    @FXML
    private TextField su_email;
    @FXML
    private TextField su_username;
    @FXML
    private PasswordField su_password;
    @FXML
    private Button su_signupBtn;
    @FXML
    private AnchorPane login_form;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField si_email; // Champ de texte pour l'email

    @FXML
    private PasswordField si_password;


    // Champ de texte pour le mot de passe

    IServiceUser serviceUser=new ServiceUser();
    @FXML
    private void login() {

        String email = si_email.getText(); // Récupérer l'email depuis le champ de texte
        String password = si_password.getText(); // Récupérer le mot de passe depuis le champ de texte
        System.out.println(email+" "+password);

        // Appeler la méthode de connexion avec les valeurs récupérées
        int i= serviceUser.Authentification(email, password);
        if(i==1){
            System.out.println("login success");
        }
    private void login(ActionEvent event) throws IOException {

        String email = si_email.getText(); // Récupérer l'email depuis le champ de texte
        String password = si_password.getText(); // Récupérer le mot de passe depuis le champ de texte
        System.out.println(email + " " + password);

        // Appeler la méthode de connexion avec les valeurs récupérées
        int i = serviceUser.Authentification(email, password);
        if (i == 1) {
            System.out.println("login success");
            FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/dashboardX.fxml"));
            Parent dashboardRoot = dashboardLoader.load();
            Scene dashboardScene = new Scene(dashboardRoot);

            // Accéder au contrôleur du tableau de bord si nécessaire
            // DashboardController dashboardController = dashboardLoader.getController();

            // Obtenir la scène principale et changer la scène actuelle
            Stage primaryStage = (Stage) si_email.getScene().getWindow();
            primaryStage.setScene(dashboardScene);
            primaryStage.setTitle("Dashboard");


    }
        else{
            System.out.println("login failed");
        }
    }

    @FXML
    private void signup() {
        String email = su_email.getText(); // Récupérer l'email depuis le champ de texte
        String username = su_username.getText(); // Récupérer le nom d'utilisateur depuis le champ de texte
        String password = su_password.getText();
        String telehone = su_telephone.getText();// Récupérer le mot de passe depuis le champ de texte
        Role defaultRole = Role.MEMBRE; // Définissez le rôle par défaut ici
        User u = new User(username,email,password,telehone, defaultRole);
        // Appeler la méthode d'inscription avec les valeurs récupérées
         serviceUser.register(u);
        User u = new User(username,email,password,telehone, defaultRole,null);
        // Appeler la méthode d'inscription avec les valeurs récupérées
         serviceUser.register(u);

        System.out.println("signup success");

    }

    public void close(){
        javafx.application.Platform.exit();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

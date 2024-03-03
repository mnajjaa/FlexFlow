package com.example.bty.Controllers;

import com.example.bty.Entities.User;
import com.example.bty.Entities.Validation;
import com.example.bty.Services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.util.Random;

public class RestPwdController {
    public Button resetCode_btn;
    public TextField confrmPwd_txtfd1;
    public TextField newPwd_txtfd;
    public Label error_lbl;
    public TextField userEmail_txtfd;
    public Button resetpwd_btn;

IServiceValidation serviceValidation = new ServiceValidation(); //sna3et instance mel validation Service
IServiceUser serviceUser = new ServiceUser(); // sna3et instance mel user Service
        MailerService mailerService = new MailerService();
    public void resetPWD(ActionEvent event) {
        String email = userEmail_txtfd.getText().trim();
        String newPwd = newPwd_txtfd.getText().trim();
        String confrmPwd = confrmPwd_txtfd1.getText().trim();

        if (email.isEmpty() || newPwd.isEmpty() || confrmPwd.isEmpty()) {
            error_lbl.setText("All fields are required!");
            return;
        }

        if (!newPwd.equals(confrmPwd)) {
            error_lbl.setText("Passwords do not match!");
            return;
        }


        if (serviceUser.emailExists(email)) {
            System.out.println("User already exist");
            Random random = new Random();
            random.nextInt(9999); // code bin 0 w 8999 [0,9999[
            Validation v = new Validation(random.nextInt(9999), Instant.now(), Instant.now().plusSeconds(600), serviceUser.findByEmail(email));
            serviceValidation.ajouterValidation(v);
            String message = "Your code is : " + v.getCode();
            String subject = "Rénitialisation mot de passe";
            mailerService.sendMail(email, message, subject);

//            User user = serviceUser.findByEmail(email); // jebet user eli aandou adresse :
//            Validation validation = serviceValidation.findByIdUser(user.getId());


        } else {
            System.out.println("User not exist");
        }





        //redirect to code verification page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/verifCode.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) resetpwd_btn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

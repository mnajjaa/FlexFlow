package com.example.bty;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        IServiceUser serviceUser = new ServiceUser();
        Role userRole = Role.COACH;

        User u=new User("coach1","coach1@gmail.com","ibtihel1234","12345678",userRole);

         //User u=new User("ibtihel","ibtihel.mnaja123@gmail.com","ibtihel1234");
    /* if(serviceUser.emailExists(u.getEmail()))
        {
            System.out.println("User already exist");
        }
        else
            serviceUser.register(u);*/

        int status = serviceUser.Authentification("coach1@gmail.com", "ibtihel1234");
      switch (status) {
            case 0:
                System.out.println("Invalid user credentials");
                break;
            case 1:
                System.out.println("Logged in successfully");
                break;
            case 2:
                System.out.println("User is desactiver");
                break;
        }

        Session s=Session.getInstance();
        System.out.println(s.getLoggedInUser());
         //s.logout();
        //System.out.println(s.getLoggedInUser());
        //serviceUser.Authentification("ibtihel.mnaja123@gmail.com", "ibtihel1234");
         //System.out.println(s.getLoggedInUser());



        //tester la methode ActiverOrDesactiver

        serviceUser.ActiverOrDesactiver(2);
    }
}
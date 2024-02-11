package com.example.bty;

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

        //User u=new User("dali","dali.trabelsi@gmail.com","dali1234");
        //serviceUser.register(u);
        // User u=new User("dali","dali.trabelsi123@gmail.com","dali1234");
        /*if(serviceUser.emailExists(u.getEmail()))
        {
            System.out.println("User already exist");
        }
        else
            serviceUser.register(u);

*/
        if (serviceUser.Authentification("dali.trabelsi123@gmail.com", "dali1234"))
        {
            System.out.println("Authentification réussie");
        }
        else
            System.out.println("Email or Password are Invalid");

        Session s=Session.getInstance();
        System.out.println(s.getLoggedInUser());
        // s.logout();
        //System.out.println(s.getLoggedInUser());
        //serviceUser.Authentification("dali.trabelsi123@gmail.com", "dali1234");
        // System.out.println(s.getLoggedInUser());


    }
}
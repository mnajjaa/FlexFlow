package com.example.bty;

import com.example.bty.Entities.Evenement;
import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceEvenement;

import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HelloApplication extends Application {
 /*   @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }*/

    public static void main(String[] args) throws ParseException {
//        IServiceUser serviceUser = new ServiceUser();

        //User u=new User("dali","dali.trabelsi@gmail.com","dali1234");
        //serviceUser.register(u);
        // User u=new User("dali","dali.trabelsi123@gmail.1com","dali1234");
        /*if(serviceUser.emailExists(u.getEmail()))
        {
            System.out.println("User already exist");
        }
        else
            serviceUser.register(u);

*/
//        if (serviceUser.Authentification("dali.trabelsi123@gmail.com", "dali1234"))
//        {
//            System.out.println("Authentification réussie");
//        }
//        else
//            System.out.println("Email or Password are Invalid");
//
//        Session s=Session.getInstance();
//        System.out.println(s.getLoggedInUser());
        // s.logout();
        //System.out.println(s.getLoggedInUser());
        //serviceUser.Authentification("dali.trabelsi123@gmail.com", "dali1234");
        // System.out.println(s.getLoggedInUser());



//        Evenement e1 = new Evenement("saintvalentinEvent", Timestamp.valueOf("2024-02-12 20:49:14")
//                , 30, "deficie calorie", "hit",u1,true);

      User u1 = new User( 1,"farah", "farah20@gmail.com", "23565", "26801168", Role.COACH);


        Evenement e=new Evenement("yoga", Timestamp.valueOf("2021-05-20 10:10:10"),10,"sport","bien etre",u1,true);
        ServiceEvenement se = new ServiceEvenement();
        se.ajouterEvenement(e);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
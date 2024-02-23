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
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

public class HelloApplication extends Application {
    private static IServiceUser serviceUser;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ParseException {


        //User u=new User("dali","dali.trabelsi@gmail.com","dali1234");
        //serviceUser.register(u);
        // User u=new User("dali","dali.trabelsi123@gmail.1com","dali1234");
        //if(serviceUser.emailExists(u.getEmail()))


        IServiceUser serviceUser = new ServiceUser();
        Role userRole = Role.COACH;

        User u = new User("hela", "hela@gmail.com", "salma1234", "12345678", userRole);

        //User u=new User("ibtihel","ibtihel.mnaja123@gmail.com","ibtihel1234");
//    if(serviceUser.emailExists(u.getEmail()))
//        {
//            System.out.println("User already exist");
//        }
//        else
//            serviceUser.register(u);


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

        int status = serviceUser.Authentification("salma1@gmail.com", "salma1234");
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

        Session s = Session.getInstance();
        System.out.println(s.getLoggedInUser());
        //s.logout();

        //System.out.println(s.getLoggedInUser());
        //serviceUser.Authentification("ibtihel.mnaja123@gmail.com", "ibtihel1234");
        //System.out.println(s.getLoggedInUser());
        //tester la methode ActiverOrDesactiver

        //   serviceUser.ActiverOrDesactiver(6);
//


        //test ajout
        User u1 = new User(6, "hela", "hela@gmail.com", "salma1234", "12345678", Role.COACH);
        Date sqlDate = Date.valueOf("2022-12-03");
        Time sqlTime = Time.valueOf("15:45:00");

      ///  Evenement e = new Evenement("BodyAttack", "hit " , "bruler Graisse", 30, sqlDate,sqlTime,u1,false);

        ServiceEvenement se = new ServiceEvenement();
//        se.ajouterEvenement(e);
//       System.out.println("evenement added succesfully ");
        //test modifier
//        Evenement EventToUpdate = new Evenement("salsa", Timestamp.valueOf("2021-05-20 10:10:10"), 15, "sport", "bien etre", u1, false,"20:00:00");
//         EventToUpdate.setId(29);
//         se.modifierEvenement(EventToUpdate);
//        System.out.println("evenement updated succesfully ");

        //test suppresion
//        se.supprimerEvenement(25);
//        System.out.println("evenement deleted succesfully ");

       // test affichage lists des evenements
       //   se.consulterEvenement().forEach(System.out::println);
        List<Evenement> events = se.consulterEvenements();

        for (Evenement event : events) {
            System.out.println("Evenement{id=" + event.getId() +
                    ", name=" + event.getNom() +
                    ", categorie='" + event.getCategorie() +
                    "', objectif='" + event.getObjectif() +
                    ", nbre_place=" + event.getNbre_place() +
                    ", date=" + event.getDate() +
                    ", Time="+event.getTime()+
                    "', coach=" + event.getCoach().getId() +
                    "', nom coach=" + event.getCoach().getName()+
                    ", etat=" + event.isEtat() +
                    "}");
        }





    }
}
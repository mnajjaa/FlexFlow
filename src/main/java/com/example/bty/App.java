package com.example.bty;

import com.example.bty.Entities.*;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceDemande;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {


        IServiceUser serviceUser = new ServiceUser();
        Role userRole = Role.ADMIN;

        User u=new User("farah","farah@gmail.com","ibtihel1234","12345678",userRole);


        User u1 = new User(1, "farah", "farah20@gmail.com", "23565", "26801168", Role.COACH);
       User u2 = new User(3, "hajer", "hajer@gmail.com", "2365", "53028810", Role.MEMBRE);

       // Offre f1 = new Offre(5, Specialite.BOXE, 25,u1);
        Offre f = new Offre(2, Specialite.BOXE, 20,u1);
        Demande d = new Demande(1, 21, 6, false, "maigrir", "moyenne", u1,f ,Etat.REFUSER );
        //Demande d2 = new Demande(5, 21, 4, true, "maigrir", "moyenne", u1,f ,Etat.REFUSER);
        ServiceDemande ps = new ServiceDemande();
        //ps.addDemande(d2);
        //ps.addDemande(d);
        //ServiceDemande ps1 = new ServiceDemande();
       // ps.DeleteDemande(1);

        //ps.UpdateDemande(d);
        //ServiceOffre off = new ServiceOffre();
            // off.addOffre(f1);


         //User u=new User("ibtihel","ibtihel.mnaja123@gmail.com","ibtihel1234");

        //**tester la methode register
 /*  if(serviceUser.emailExists(u.getEmail()))
        {
            System.out.println("User already exist");
        }
        else
            serviceUser.register(u);*/

       // int status = serviceUser.Authentification("coach1@gmail.com", "ibtihel1234");
     // switch (status) {
         //   case 0:
            //    System.out.println("Invalid user credentials");
            //    break;
           // case 1:
           //     System.out.println("Logged in successfully");
             //   break;
           // case 2:
            //    System.out.println("User is desactiver");
               // break;
       // }
        //**tester la methode Authentification
      //  int status = serviceUser.Authentification("mnajjaibtihel@gmail.com", "ibtihel1234");
     // switch (status) {
           // case 0:
               // System.out.println("Invalid user credentials");
               // break;
           // case 1:
              //  System.out.println("Logged in successfully");
                //break;
           // case 2:
              //  System.out.println("User is desactiver");
               // break;
       // }

        Session s=Session.getInstance();
        System.out.println(s.getLoggedInUser());
//         s.logout();
//        System.out.println(s.getLoggedInUser());
//        serviceUser.Authentification("ibtihel.mnaja123@gmail.com", "ibtihel1234");
//         System.out.println(s.getLoggedInUser());



        //**tester la methode ActiverOrDesactiver
        //serviceUser.ActiverOrDesactiver(2);

        //**tester la methode update
       /* User userUpdate = new User();
        userUpdate.setId(6);
        userUpdate.setName("Admin1");
        userUpdate.setEmail("Admin1@gmail.com");
        userUpdate.setPassword("Admin1234");
        userUpdate.setTelephone("12345678");
        userUpdate.setRole(userRole);
        //appel de la methode update
        //serviceUser.update(userUpdate);

        serviceUser.ActiverOrDesactiver(2);*/
    }
}
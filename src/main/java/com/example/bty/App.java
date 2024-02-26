package com.example.bty;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Models.Model;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import com.example.bty.Views.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/FXML/Login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setScene(scene);
//        stage.show();

//        ViewFactory viewFactory = new ViewFactory();
//        viewFactory.showLoginWindow();

        Model.getInstance().getViewFactory().showLoginWindow();
    }

    public static void main(String[] args) {


        IServiceUser serviceUser = new ServiceUser();
        Role userRole = Role.ADMIN;

        User u=new User("farah","farah@gmail.com","ibtihel1234","12345678",userRole);

        //User u=new User("ibtihel","ibtihel.mnaja123@gmail.com","ibtihel1234");

        //**tester la methode register
 /*  if(serviceUser.emailExists(u.getEmail()))
        {
            System.out.println("User already exist");
        }
        else
            serviceUser.register(u);*/

        //**tester la methode Authentification
        int status = serviceUser.Authentification("mnajjaibtihel@gmail.com", "ibtihel1234");
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

        Session s= Session.getInstance();
        System.out.println(s.getLoggedInUser());
//         s.logout();
//        System.out.println(s.getLoggedInUser());
//        serviceUser.Authentification("ibtihel.mnaja123@gmail.com", "ibtihel1234");
//         System.out.println(s.getLoggedInUser());



        //**tester la methode ActiverOrDesactiver
        serviceUser.ActiverOrDesactiver(15);

        //**tester la methode update
        User userUpdate = new User();
        userUpdate.setId(6);
        userUpdate.setName("Admin1");
        userUpdate.setEmail("Admin1@gmail.com");
        userUpdate.setPassword("Admin1234");
        userUpdate.setTelephone("12345678");
        userUpdate.setRole(userRole);
        //appel de la methode update
        //serviceUser.update(userUpdate);

        //**tester la methode delete
        //serviceUser.delete(9);

        //tester la methode getAllMembers
        // System.out.println(serviceUser.getAllMembers());

        //tester la methode getAllCoaches
        //System.out.println(serviceUser.getAllCoaches());

        //launch();

    }

}
package com.example.bty;

import com.example.bty.Entities.Cours;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceCours;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
        Cours c1 = new Cours("Yoga","10","Fort","Enfant","Cardio","perdre poid",20,true);

        ServiceCours CS = new ServiceCours();


        CS.addPst(c1);
        //CS.addPst(c2);
        //CS.addPst(c3);
        //CS.addPst(c4);
        //CS.addPst(c5);

        //CS.DeleteCours(8);
        //CS.DeleteCours(5);

        //CS.consulterCours().forEach(System.out::println);

        //update
        //Cours coursToUpdate = new Cours("Defoulement", "5", "Moyen", "Enfant", "Kids island", "S'amuser",10);
        //coursToUpdate.setIdCour(7); // Mettez l'ID du cours que vous souhaitez mettre à jour
        //CS.UpdateCours(coursToUpdate);


        //ps.readAll().forEach(System.out::println);

        // Filtrer les cours en fonction des critères spécifiés
        //List<Cours> coursFiltres = CS.filtrerCours("kids island", "Adulte", "Gagner de la force");

        // Afficher les cours filtrés
        //System.out.println("Cours filtrés : ");
        //for (Cours cours : coursFiltres) {
           // System.out.println(cours);
       // }
    }

}
//nasna3 user 9bal


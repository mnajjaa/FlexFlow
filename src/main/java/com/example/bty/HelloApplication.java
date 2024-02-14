package com.example.bty;

import com.example.bty.Entities.Cours;
import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceCours;
import com.example.bty.Services.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
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

    public static void main(String[] args) throws SQLException {
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

        User u1 = new User( 1,"farah", "farah20@gmail.com", "23565", "26801168", Role.COACH);
        User u2 = new User( 2,"baha", "bahadridi@gmail.com", "2001", "24509366", Role.COACH);

        User u3 = new User( 3,"baha", "bahadridi@gmail.com", "2001", "24509366", Role.MEMBRE);
        User u4 = new User( 4,"baha", "bahadridi@gmail.com", "2001", "24509366", Role.MEMBRE);
        User u7 = new User( 6,"ahmed", "ahmed@gmail.com", "0000", "123456", Role.MEMBRE);

        //ADD new cour
        Cours c1 = new Cours("Yoga","10","Fort","Enfant","Cardio","perdre poid",true,20,u1);
        Cours c2 = new Cours("BodyAttack","20","Fort","Adulte","RPM","gagner de la force",true,30,u2);
        Cours c3 = new Cours("BodyAttack","20","Fort","Adulte","RPM","gagner de la force",true,2,u3);
        Cours c4 = new Cours("zumba","20","Fort","Adulte","RPM","gagner de la force",true,4,u4);
        //Cours c5 = new Cours("BodyAttack","20","Fort","Adulte","RPM","gagner de la force",true,2,u5);
        ServiceCours CS = new ServiceCours();






        //ADD cour fel base
        //CS.addPst(c1);
        //CS.addPst(c2);
        //CS.addPst(c3);
        //CS.addPst(c4);
        //CS.addPst(c5);

        //DELETE cour
        //CS.DeleteCours(4);
        //CS.DeleteCours(5);


        //Lister cour shihaaa
        //List<Cours> coursList = CS.consulterCours();
        //for (Cours cours : coursList) {
            //System.out.println("Cours{id=" + cours.getId() + ", nom='" + cours.getNom() + "', duree='" + cours.getDuree() + "', intensite='" + cours.getIntensite() + "', cible='" + cours.getCible() + "', categorie='" + cours.getCategorie() + "', objectif='" + cours.getObjectif() + "', etat=" + cours.isEtat() + ", capacité=" + cours.getCapacite() + ", coach=" + cours.getCoach().getId() +  "}");
        //}


        List<Cours> coursList = CS.consulterCours();
        for (Cours cours : coursList) {
            System.out.println("Cours{id=" + cours.getId() + ", nom='" + cours.getNom() + "', duree='" + cours.getDuree() + "', intensite='" + cours.getIntensite() + "', cible='" + cours.getCible() + "', categorie='" + cours.getCategorie() + "', objectif='" + cours.getObjectif() + "', etat=" + cours.isEtat() + ", capacité=" + cours.getCapacite() + ", coach=" + cours.getCoach().getId() + ", coachName=" + cours.getCoach().getName() + "}");
        }


        //CS.consulterCours().forEach(System.out::println);

        //update cour
        //Cours coursToUpdate = new Cours("Defoulement", "5", "Moyen", "Enfant", "Kids island", "s'amuser",true,10,u1);
        //coursToUpdate.setId(3); // Mettez l'ID du cours que vous souhaitez mettre à jour
       //CS.UpdateCours(coursToUpdate);


        //ps.readAll().forEach(System.out::println);

        // Filtrer les cours en fonction des critères spécifiés
        //List<Cours> coursFiltres = CS.filtrerCours("kids island", "Adulte", "Gagner de la force");

        // Afficher les cours filtrés
        //System.out.println("Cours filtrés : ");
        //for (Cours cours : coursFiltres) {
           // System.out.println(cours);
       // }



        // Filtrer les cours en fonction des critères spécifiés
        //List<Cours> coursFiltres = ServiceCours.filtrerCours("Cardio", "Adulte", "gagner de la force");
        // Afficher les cours filtrés
        //System.out.println("Cours filtrés : ");
        //for (Cours cours : coursFiltres) {
            //System.out.println(cours);
       // }
    }

}
//nasna3 user 9bal


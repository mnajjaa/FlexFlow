package com.example.bty.Services;

import com.example.bty.Entities.Evenement;
import com.example.bty.Utils.ConnexionDB;

import java.sql.*;

public class ServiceEvenement {
    private Connection connexion;
    private PreparedStatement pde;
Boolean etat=true;
    public ServiceEvenement(){
        connexion= ConnexionDB.getInstance().getConnexion();
    }
//public  void addEvent (Evenement E){
//
//    String requete="insert into evenement (nomEvenement,Date,nbrPlace,Objectif,categorie,coach,etat) values(?,?,?,?,?,?,?)";
//    try {
//        pde=connexion.prepareStatement(requete);
//        pde.setString(1,E.getNom());
//        pde.setTimestamp(2,E.getDate());
//        pde.setInt(3,E.getNbre_place());
//        pde.setString(4,E.getObjectif());
//        pde.setString(5,E.getCategorie());
//        pde.setString(6,E.getCoach().getName());
//        pde.setBoolean(7,E.isEtat());
//        pde.executeUpdate();
//    } catch (SQLException e) {
//        throw new RuntimeException(e);
//    }
//}

    //Methode pour ajouter un evenement
    public void ajouterEvenement(Evenement e){
        String req="INSERT INTO evenement (nomEvenement,Date,nbrplace,categorie,objectif,id_user,etat) VALUES (?,?,?,?,?,?,?)";
        try {
            pde=connexion.prepareStatement(req);
            pde.setString(1,e.getNom());
            pde.setTimestamp(2,e.getDate());
            pde.setInt(3,e.getNbre_place());
            pde.setString(4,e.getCategorie());
            pde.setString(5,e.getObjectif());
            pde.setInt(6,e.getCoach().getId());
            pde.setBoolean(7, e.isEtat());
            pde.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }




}

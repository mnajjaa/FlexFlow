package com.example.bty.Services;

import com.example.bty.Entities.Evenement;
import com.example.bty.Entities.User;
import com.example.bty.Utils.ConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement {
    private Connection connexion;
    private PreparedStatement pde;
Boolean etat=true;
    public ServiceEvenement(){
        connexion= ConnexionDB.getInstance().getConnexion();
    }


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
    //methode pour modifier un evenement
    public void modifierEvenement(Evenement e){
        String req="UPDATE evenement SET nomEvenement=?,Date=?,nbrPlace=?,categorie=?,Objectif=?,id_user=?,etat=? WHERE id_evenement=?";
        try {
            pde=connexion.prepareStatement(req);
            pde.setString(1,e.getNom());
            pde.setTimestamp(2,e.getDate());
            pde.setInt(3,e.getNbre_place());
            pde.setString(4,e.getCategorie());
            pde.setString(5,e.getObjectif());
            pde.setInt(6,e.getCoach().getId());
            pde.setBoolean(7,e.isEtat());
            pde.setInt(8,e.getId());
            pde.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//methode pour supprimer un evenement

    public void supprimerEvenement(int id){
        String req="DELETE FROM evenement WHERE id_evenement=?";
        try {
            pde=connexion.prepareStatement(req);
            pde.setInt(1,id);
            pde.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // Méthode pour consulter tous les événements
    public List<Evenement> consulterEvenements() {
        List<Evenement> evenements = new ArrayList<>();

        String query = "SELECT * FROM evenement";

        try (Connection connection = ConnexionDB.getInstance().getConnexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Evenement E = new Evenement();
                E.setId(resultSet.getInt("id_evenement"));
                E.setNom(resultSet.getString("nomEvenement"));
                E.setDate(resultSet.getTimestamp("Date"));
                E.setNbre_place(resultSet.getInt("nbrPlace"));
                E.setCategorie(resultSet.getString("categorie"));
                E.setObjectif(resultSet.getString("Objectif"));

                User coach = new User();
                coach.setId(resultSet.getInt("id_user"));
                E.setCoach(coach);

                E.setEtat(resultSet.getBoolean("etat"));

                evenements.add(E);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evenements;
    }



}

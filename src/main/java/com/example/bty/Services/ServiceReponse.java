package com.example.bty.Services;

import com.example.bty.Entities.Reclamation;

import com.example.bty.Entities.Reponse;
import com.example.bty.Utils.ConnexionDB;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceReponse {
    private Connection connexion;

    private Statement ste;
    Date Datenow= java.sql.Date.valueOf(LocalDate.now());


    public ServiceReponse() {
        connexion = ConnexionDB.getInstance().getConnexion();
    }
    //ajouter reponse
    public void addReponse(Reponse reponse) {
        String requete = "insert into reponse (reponse_reclamation,id_reclamation) values ('"
                + reponse.getReponse_reclamation() + "','" + reponse.getReclamation().getId() + "')";

        try {
            Statement statement = connexion.createStatement();
            statement.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //modifer la reponse
    public void updateReponseReclamation(int reponseId, String newReponse) {
        String query = "UPDATE reponse SET reponse_reclamation = '" + newReponse + "' WHERE id = " + reponseId;

        try {
            Statement statement = connexion.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //probleme type
//        public List<Reponse> readallreponse(){
//            String requete = "select * from reponse";
//            List<Reponse> list = new ArrayList<>();
//            try {
//                ste= conn.createStatement();
//                ResultSet rp = ste.executeQuery(requete);
//                while(rp.next()){
//
//
//                    list.add(new Reponse(rp.getInt("id_reponse"),rp.getString("reponse_reclamation"),rp.getInt("id_reclamation")));
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            return list;
//}




    //FONCTIONELLe sans l'id de la reclamation

    public List<Reponse> readallreponsev2() {
        String requete = "SELECT * FROM reponse"; // Sélectionner toutes les colonnes de la table reponse
        List<Reponse> list = new ArrayList<>();

        try {
            Statement ste = connexion.createStatement();
            ResultSet rp = ste.executeQuery(requete);
            while (rp.next()) {
                int reponseId = rp.getInt("id");
                String reponseReclamation = rp.getString("reponse_reclamation");
                int reclamationId = rp.getInt("id_reclamation");

                // Récupérer la réclamation associée à partir de son ID
                Reclamation reclamation = getReclamationById(reclamationId);

                // Créer et ajouter l'objet Reponse à la liste
                list.add(new Reponse(reponseId, reponseReclamation, reclamation));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Méthode pour récupérer une réclamation par son ID

    public Reclamation getReclamationById(int reclamationId) {
        Reclamation reclamation = null;
        String query = "SELECT * FROM reclamation WHERE id = " + reclamationId;

        try {
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                // Récupération des données de la réclamation depuis le ResultSet
                int id = resultSet.getInt("id");
                Date dateReclamation = resultSet.getDate("date_reclamation");
                String titreReclamation = resultSet.getString("titre_reclamation");
                String description = resultSet.getString("description");
                String etat = resultSet.getString("etat");

                // Création de l'objet Reclamation
                reclamation = new Reclamation(id, dateReclamation, titreReclamation, description, etat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reclamation;
    }


    //optimal mais sans la liste
//    public void displayAllReponsesWithReclamationId() {
//        String query = "SELECT * FROM reponse";
//
//        try {
//            Statement statement = connexion.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                int reponseId = resultSet.getInt("id");
//                String reponseReclamation = resultSet.getString("reponse_reclamation");
//                int reclamationId = resultSet.getInt("id_reclamation");
//
//                System.out.println(" ID reponse " + reponseId + " reponse " + reponseReclamation +" ID reclamation " + + reclamationId);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }













}




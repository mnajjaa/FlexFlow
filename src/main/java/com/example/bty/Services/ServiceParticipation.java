package com.example.bty.Services;

import com.example.bty.Entities.Cours;
import com.example.bty.Entities.Participation;
import com.example.bty.Entities.User;
import com.example.bty.Utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceParticipation {
    private static Connection connexion;
    private PreparedStatement pst;

    public ServiceParticipation() {
        connexion = ConnexionDB.getInstance().getConnexion();
    }

    public void participerAuCours(User user, Cours cours) {
        String requete = "INSERT INTO participation (id_user , nomCour) VALUES (?, ?)";
        try {
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, user.getId());
            pst.setString(2, cours.getNom()); // Ajouter le nom du cours à la requête
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

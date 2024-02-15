package com.example.bty.Services;

import com.example.bty.Entities.Demande;
import com.example.bty.Utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;



    public class ServiceDemande {
        private Connection connexion;
        private Statement ste;
        private PreparedStatement pst;
        public ServiceDemande() {
            connexion= ConnexionDB.getInstance().getConnexion();
        }

//premiére methode
        //public void add(Demande d){
        //  String requete="insert into Demande (id,age,nbre_heure,maladie_chronique,but,niveau_physique, membre,offre) values ('"+d.getId()+"','"+d.getAge()+"' ,'"+d.getNbre_heure()+"' ,'"+d.getMaladie_chronique()+"' , '"+d.getBut()+"','"+d.getNiveau_physique()+"','"+d.getOffre()+"', '"+d.getMembre()+"')";

        // try {
        // ste=connexion.createStatement();
//ste.executeUpdate(requete);
        // } catch (SQLException e) {
        // throw new RuntimeException(e);
//}
        //  }


        public void addDemande(Demande d) {

            //try (Connection connection = DataSource.obtenirConnexion())
            String query = "INSERT INTO Demande (id_demande,Age,nombreHeure,MaladieChronique,But,NiveauPhysique,id_user,id_Offre) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
            try (PreparedStatement statement = connexion.prepareStatement(query)) {
                statement.setInt(1, d.getId_demande());
                statement.setInt(2, d.getAge());
                statement.setInt(3, d.getNbre_heure());
                statement.setBoolean(4, d.getMaladie_chronique());
                statement.setString(5, d.getBut());
                statement.setString(6, d.getNiveau_physique());
                statement.setInt(7, d.getMembre().getId());
                statement.setInt(8, d.getOffre().getId());

                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public void DeleteDemande (int id) {
            String DELETE = "DELETE FROM Demande WHERE id_demande = ?";
            try {
                pst = connexion.prepareStatement(DELETE);
                pst.setInt(1, id);
                pst.executeUpdate();
                System.out.println("Demande supprimée avec succès !");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }}

        public void UpdateDemande(Demande d) throws SQLException {
            String UPDATE = "UPDATE Demande SET id_demande = ?, Age = ?, nombreHeure = ?, MaladieChronique = ?, But = ?, NiveauPhysique = ?, id_user = ? WHERE id_offre = ?";
            try {
                pst = connexion.prepareStatement(UPDATE);
                pst.setInt(1, d.getAge());
                pst.setInt(2, d.getNbre_heure());
                pst.setBoolean(3, d.isMaladieCHronique);
                pst.setString(4, d.getBut());
                pst.setString(5, d.getNiveau_physique());
                pst.setInt(6, d.getMembre().getId());
                pst.setInt(7, d.getOffre().getId());
                pst.setInt(8, d.getId());
                pst.executeUpdate();
                System.out.println("Demande mise à jour avec succès !");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }



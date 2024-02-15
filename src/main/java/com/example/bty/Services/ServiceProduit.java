package com.example.bty.Services;
import com.example.bty.Entities.Commande;
import com.example.bty.Entities.Produit;
import com.example.bty.Utils.ConnexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit {
    private Connection connexion;
    private PreparedStatement pst;
    private Statement ste ;
    public ServiceProduit() {
        connexion = ConnexionDB.getInstance().getConnexion();
    }


    public void ajouterProduit(Produit produit) {

        //try (Connection connection = DataSource.obtenirConnexion())
        String query = "INSERT INTO produit (idProduit,nom, description, prix, type, quantite, quantiteVendues) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, produit.getIdProduit());
            statement.setString(2, produit.getNom());
            statement.setString(3, produit.getDescription());
            statement.setDouble(4, produit.getPrix());
            statement.setString(5, produit.getType());
            statement.setInt(6, produit.getQuantite());
            statement.setInt(7, produit.getQuantiteVendues());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produit> consulterProduits() {
        List<Produit> produits = new ArrayList<>();
        // try (Connection connection = ConnexionDB.obtenirConnexion())
        String query = "SELECT * FROM produit";
        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Produit produit = new Produit();

                produit.setIdProduit(resultSet.getInt("idProduit"));
                produit.setNom(resultSet.getString("nom"));
                produit.setDescription(resultSet.getString("Description"));
                produit.setPrix(resultSet.getDouble("Prix"));
                produit.setType(resultSet.getString("Type"));
                produit.setQuantite(resultSet.getInt("Quantite"));
                produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));

                produits.add(produit);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    public void modifierPrixProduit(int idProduit, double nouveauPrix) {
        // try (Connection connection = ConnexionDB.obtenirConnexion()) {
        String query = "UPDATE produit SET Prix = ? WHERE idProduit = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setDouble(1, nouveauPrix);
            statement.setInt(2, idProduit);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerProduit(int idProduit) {
        //try (Connection connection = ConnexionDB.obtenirConnexion()) {
        String query = "DELETE FROM produit WHERE idProduit = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, idProduit);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Produit> rechercherProduitsParType(String type) {
        List<Produit> produits = new ArrayList<>();
        //try (Connection connection = ConnexionDB.obtenirConnexion()) {
        String query = "SELECT * FROM produit WHERE type = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, type);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getInt("idProduit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setType(resultSet.getString("type"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));
                    produits.add(produit);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }


    public List<Produit> filtrerProduitsParPlageDePrix(int prixMin, int prixMax) {
        List<Produit> produits = new ArrayList<>();
        //try (Connection connection = ConnexionDB.obtenirConnexion()) {
        String query = "SELECT * FROM produit WHERE prix BETWEEN ? AND ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setDouble(1, prixMin);
            statement.setDouble(2, prixMax);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getInt("idProduit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getInt("prix"));
                    produit.setType(resultSet.getString("type"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));

                    produits.add(produit);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }



    public void mettreAJourQuantiteVendueEtTotale(Produit produit, int quantiteAchete) {
        String query = "UPDATE produit SET quantiteVendues = quantiteVendues + ?, quantite = quantite - ? WHERE idProduit = ?";

        try (
                PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, quantiteAchete);
            statement.setInt(2, quantiteAchete);
            statement.setInt(3, produit.getIdProduit());

            int lignesModifiees = statement.executeUpdate();

            if (lignesModifiees > 0) {
                System.out.println("Mise à jour réussie.");
            } else {
                System.out.println("Aucune mise à jour effectuée. Vérifiez l'ID du produit.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Produit mapperProduit(ResultSet resultSet) throws SQLException {
        Produit produit = new Produit();
        produit.setIdProduit(resultSet.getInt("idProduit"));
        produit.setNom(resultSet.getString("nom"));
        produit.setDescription(resultSet.getString("description"));
        produit.setPrix(resultSet.getDouble("prix"));
        produit.setType(resultSet.getString("type"));
        produit.setQuantite(resultSet.getInt("quantite"));
        produit.setQuantiteVendues(resultSet.getInt("quantiteVendues"));



        return produit;
    }

    public Produit obtenirMeilleurVendeur() {
        Produit meilleurVendeur = null;
        String query = "SELECT * FROM produit ORDER BY quantiteVendues DESC LIMIT 1";

        try (
                PreparedStatement statement = connexion.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                meilleurVendeur = mapperProduit(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meilleurVendeur;
    }

    public double calculerChiffreAffairesTotal() {
        double chiffreAffairesTotal = 0.0;
        String query = "SELECT SUM(quantiteVendues * prix) AS chiffreAffaires FROM produit";

        try (
                PreparedStatement statement = connexion.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                chiffreAffairesTotal = resultSet.getDouble("chiffreAffaires");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chiffreAffairesTotal;
    }




    public void afficherColonnesProduit() {
        String query = "SELECT idProduit,nom, description, prix, type, quantite FROM produit";

        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Affichage des noms de colonnes
            System.out.println("La liste des produits disponible :");
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Affichage des données
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public void ajouterCommande(Timestamp dateCommande, double montantTotal,Integer id_user) {
        String query = "INSERT INTO Commande (dateCommande, montantTotal,id_user) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setTimestamp(1, dateCommande); // Convertit la date Java en java.sql.Date
            statement.setDouble(2, montantTotal);
            statement.setDouble(3, id_user);


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }















}

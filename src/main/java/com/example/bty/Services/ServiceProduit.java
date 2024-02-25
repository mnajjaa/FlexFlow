package com.example.bty.Services;
import com.example.bty.Entities.Commande;
import com.example.bty.Entities.Commmande;
import com.example.bty.Entities.Produit;
import com.example.bty.Entities.User;
import com.example.bty.Utils.ConnexionDB;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceProduit {
    private Connection connexion;
    private PreparedStatement pst;
    private Statement ste ;
    public ServiceProduit() {
        connexion = ConnexionDB.getInstance().getConnexion();
    }


  public boolean ajouterProduit(Produit produit) {
      String query = "INSERT INTO produit (idProduit, nom, Description, Prix, Type, Quantite, quantiteVendues, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

      try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "");
           PreparedStatement statement = connection.prepareStatement(query)) {
          statement.setInt(1, produit.getIdProduit());
          statement.setString(2, produit.getNom());
          statement.setString(3, produit.getDescription());
          statement.setDouble(4, produit.getPrix());
          statement.setString(5, produit.getType());
          statement.setInt(6, produit.getQuantite());
          statement.setInt(7, produit.getQuantiteVendues());
          statement.setBytes(8, produit.getImage());

          statement.executeUpdate();
          return true;
      } catch (SQLException e) {
          e.printStackTrace();
          return false;
      }
  }


    public List<Produit> consulterProduits() {
        List<Produit> produits = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "")) {
            String query = "SELECT * FROM produit";
            try (Statement statement = connection.createStatement();
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
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion ou d'exécution de la requête
        }
        return produits;
    }


    public List<Commmande> consulterCommandes() {
        List<Commmande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande"; // Assurez-vous que le nom de la table est correct

        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Commmande commande = new Commmande();

                commande.setIdCommande(resultSet.getInt("idCommande"));
                commande.setDateCommande(resultSet.getTimestamp("dateCommande"));
                commande.setIdProduit(resultSet.getInt("idProduit"));
                commande.setNom(resultSet.getString("nom"));
                commande.setMontant(resultSet.getDouble("montant"));

                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }



    public List<Commmande> consulterCommandesParDate(LocalDate selectedDate) {
        List<Commmande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande WHERE DATE(dateCommande) = ?";

        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(selectedDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Commmande commande = new Commmande();
                    commande.setIdCommande(resultSet.getInt("idCommande"));
                    commande.setDateCommande(resultSet.getTimestamp("dateCommande"));
                    commande.setIdProduit(resultSet.getInt("idProduit"));
                    commande.setNom(resultSet.getString("nom"));
                    commande.setMontant(resultSet.getDouble("montant"));
                    commandes.add(commande);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
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



    public boolean modifierProduit(Produit produit) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "")) {
            String query = "UPDATE produit SET nom=?, description=?, prix=?, type=?, quantite=?, quantiteVendues=? WHERE idProduit=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, produit.getNom());
                statement.setString(2, produit.getDescription());
                statement.setDouble(3, produit.getPrix());
                statement.setString(4, produit.getType());
                statement.setInt(5, produit.getQuantite());
                statement.setInt(6, produit.getQuantiteVendues());
                statement.setInt(7, produit.getIdProduit());

                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /*public boolean supprimerProduit(int idProduit) {
        //try (Connection connection = ConnexionDB.obtenirConnexion()) {
        String query = "DELETE FROM produit WHERE idProduit = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, idProduit);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    public boolean supprimerProduit(int idProduit) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidevgym", "root", "")) {
            String query = "DELETE FROM produit WHERE idProduit = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idProduit);
                statement.executeUpdate();
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
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






    public void ajouterCommande(Timestamp dateCommande, User user, Map<Produit, Integer> produitsDansPanier) {
        String query = "INSERT INTO Commande (dateCommande, id_user, nom_client, idProduit, nom,montant) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setTimestamp(1, dateCommande);

            statement.setInt(2, user.getId());
            statement.setString(3, user.getName());


            // Parcourir chaque produit dans le panier
            for (Map.Entry<Produit, Integer> entry : produitsDansPanier.entrySet()) {
                Produit produit = entry.getKey();
                int quantiteAchete = entry.getValue();

                // Ajouter des valeurs pour id_produit et nom_produit
                statement.setInt(4, produit.getIdProduit());
                statement.setString(5, produit.getNom());
                statement.setDouble(6, produit.getPrix() * quantiteAchete);

                statement.executeUpdate(); // Exécuter la requête pour chaque produit
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static double calculerChiffreAffaires() {
        double chiffreAffaires = 0.0;

        try (Connection connection =  ConnexionDB.getInstance().getConnexion()) {
            String query = "SELECT SUM(Montant) FROM commande";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    chiffreAffaires = resultSet.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chiffreAffaires;
    }



    public static String obtenirProduitPlusAchete() {
        String produitPlusAchete = null;

        // Utilisez try-with-resources pour vous assurer que la connexion est fermée correctement
        try (Connection connection = ConnexionDB.getInstance().getConnexion()) {
            // Votre requête SQL
            String query = "SELECT nom FROM produit ORDER BY Prix * Quantite DESC LIMIT 1";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    produitPlusAchete = resultSet.getString("nom");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produitPlusAchete;
    }






}

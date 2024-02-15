package com.example.bty;

import com.example.bty.Entities.Panier;
import com.example.bty.Entities.Produit;
import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceProduit;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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



//Module Gestion de produits
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        Produit p1 = new Produit(3,"T-shirt", "T-shirt de sport", 20, "Vetement", 50,0);
        Produit p2 = new Produit(4,"proteine", "protiene gym", 220, "proteine", 15,0);
        Produit p3 = new Produit(7,"shaker", "shaker pour melange proteine", 15, "accessoires", 5,0);
        Produit p4 = new Produit(8,"gons", "gons pour sport", 45, "accessoires", 4,0);





        ServiceProduit ps = new ServiceProduit();


     /*   ps.ajouterProduit(p1);
        ps.ajouterProduit(p2);
        ps.ajouterProduit(p3);
        ps.ajouterProduit(p4);*/





      //ps.consulterProduits().forEach(System.out::println);





       //  ps.modifierPrixProduit(4,250);




    //   ps.supprimerProduit(3);
   /*     ps.supprimerProduit(4);
        ps.supprimerProduit(7);*/







     /*   private static void afficherListeProduits(List<Produit> produits) {
            for (Produit produit : produits) {
                System.out.println("ID: " + produit.getIdProduit() +
                        ", Nom: " + produit.getNom() +
                        ", Prix: " + produit.getPrix() +
                        ", Type: " + produit.getType());
            }*/
       // ps.rechercherProduitsParType("accessoires").forEach(System.out::println);


         //ps.filtrerProduitsParPlageDePrix(10,200).forEach(System.out::println);







// ProduitDAO produitDAO = new ProduitDAO();
        Panier panier = new Panier();

        // Consulter les produits disponibles
        List<Produit> produitsDisponibles = ps.consulterProduits();
        ps.afficherColonnesProduit();

        // Ajouter des produits au panier
        Produit produit1 = produitsDisponibles.get(2);
        panier.ajouterAuPanier(produit1, 3);

       /* Produit produit2 = produitsDisponibles.get(2);
        panier.ajouterAuPanier(produit2, 4);*/

        // Afficher le panier
        panier.afficherPanier(true,ps);




        //  ps.afficherStatistiquesVentes();


        //panier.confirmerAchatEtMettreAJourQuantiteVendue(ps);

        for (Map.Entry<Produit, Integer> entry : panier.getProduitsDansPanier().entrySet()) {
            Produit produit = entry.getKey();
            int quantiteAchete = entry.getValue();

        }







      //  ps.afficherStatistiquesVentes();


        //panier.confirmerAchatEtMettreAJourQuantiteVendue(ps);

      /*  for (Map.Entry<Produit, Integer> entry : panier.getProduitsDansPanier().entrySet()) {
            Produit produit = entry.getKey();
            int quantiteAchete = entry.getValue();

        }*/




        // Afficher le meilleur vendeur
        Produit meilleurVendeur = ps.obtenirMeilleurVendeur();
        System.out.println("Meilleur produits vendus : " + meilleurVendeur.getNom() +
                ", Quantité vendue : " + meilleurVendeur.getQuantiteVendues());

// Afficher le chiffre d'affaires total
        double chiffreAffairesTotal = ps.calculerChiffreAffairesTotal();
        System.out.println("Chiffre d'affaires total : " + chiffreAffairesTotal + " DNT");




    }






    private static void afficherListeProduits(List<Produit> produits) {
        System.out.println("Liste des produits disponibles : ");
        for (Produit produit : produits) {

                    System.out.println("ID: " + produit.getIdProduit() +
                            ", Nom: " + produit.getNom() +
                            ", Prix: " + produit.getPrix() +
                            ", Type: " + produit.getType());

        }
        System.out.println();
    }


}
package com.example.bty;

import com.example.bty.Entities.Produit;
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
        if (serviceUser.Authentification("dali.trabelsi123@gmail.com", "dali1234"))
        {
            System.out.println("Authentification réussie");
        }
        else
            System.out.println("Email or Password are Invalid");

        Session s=Session.getInstance();
        System.out.println(s.getLoggedInUser());
        // s.logout();
        //System.out.println(s.getLoggedInUser());
        //serviceUser.Authentification("dali.trabelsi123@gmail.com", "dali1234");
        // System.out.println(s.getLoggedInUser());








//Module Gestion de produits
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        Produit p1 = new Produit(3,"T-shirt", "T-shirt de sport", 20, "Vetement", 50,0);
        Produit p2 = new Produit(4,"proteine", "protiene gym", 220, "proteine", 15,0);
        Produit p3 = new Produit(7,"shaker", "shaker pour melange proteine", 15, "accessoires", 5,0);


        ServiceProduit ps = new ServiceProduit();
       /* ps.ajouterProduit(p1);
        ps.ajouterProduit(p2);
        ps.ajouterProduit(p3);*/

        // ps.consulterProduits().forEach(System.out::println);

        // ps.modifierPrixProduit(1,25);

     /*   ps.supprimerProduit(3);
        ps.supprimerProduit(4);
        ps.supprimerProduit(7);*/


        /*private static void afficherListeProduits(List<Produit> produits) {
            for (Produit produit : produits) {
                System.out.println("ID: " + produit.getIdProduit() +
                        ", Nom: " + produit.getNom() +
                        ", Prix: " + produit.getPrix() +
                        ", Type: " + produit.getType());
            }*/

        //ps.rechercherProduitsParType("proteine").forEach(System.out::println);


        // ps.filtrerProduitsParPlageDePrix(10,300).forEach(System.out::println);

/*



      // ProduitDAO produitDAO = new ProduitDAO();
        Panier panier = new Panier();

        // Consulter les produits disponibles
        List<Produit> produitsDisponibles = ps.consulterProduits();
        afficherListeProduits(produitsDisponibles);

        // Ajouter des produits au panier
        Produit produit1 = produitsDisponibles.get(2);
        panier.ajouterAuPanier(produit1, 2);

        /*Produit produit2 = produitsDisponibles.get(2);
        panier.ajouterAuPanier(produit2, 4);

        // Afficher le panier
        panier.afficherPanier(true,ps);




      //  ps.afficherStatistiquesVentes();


        //panier.confirmerAchatEtMettreAJourQuantiteVendue(ps);

        for (Map.Entry<Produit, Integer> entry : panier.getProduitsDansPanier().entrySet()) {
            Produit produit = entry.getKey();
            int quantiteAchete = entry.getValue();

        }


*/

        // Afficher le meilleur vendeur
        Produit meilleurVendeur = ps.obtenirMeilleurVendeur();
        System.out.println("Meilleur vendeur : " + meilleurVendeur.getNom() +
                ", Quantité vendue : " + meilleurVendeur.getQuantiteVendues());

// Afficher le chiffre d'affaires total
        double chiffreAffairesTotal = ps.calculerChiffreAffairesTotal();
        System.out.println("Chiffre d'affaires total : " + chiffreAffairesTotal + " DH");















    }
















    private static void afficherListeProduits(List<Produit> produits) {
        System.out.println("Liste des produits disponibles : ");
        for (Produit produit : produits) {
            System.out.println(produit.toString());
        }
        System.out.println();
    }


}
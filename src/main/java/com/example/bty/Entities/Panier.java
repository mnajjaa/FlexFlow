package com.example.bty.Entities;


import com.example.bty.Services.ServiceProduit;
import com.example.bty.Services.ServiceUser;

import java.util.*;

public class Panier {


    private Map<Produit, Integer> produitsDansPanier = new HashMap<>();

    public void ajouterAuPanier(Produit produit, int quantite) {
        if (produit.getQuantite() >= quantite) {
            produitsDansPanier.put(produit, quantite);
            System.out.println("Produit ajouté au panier : " + produit.getNom() + " (Quantité: " + quantite + ")");
        } else {
            System.out.println("Quantité demandée supérieure à la quantité en stock.");
        }
    }

    public void afficherPanier(boolean confirmerAchat, ServiceProduit produitDAO) {
        double montantTotal = 0.0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Produits dans le panier : ");
        for (Map.Entry<Produit, Integer> entry : produitsDansPanier.entrySet()) {
            Produit produit = entry.getKey();
            int quantiteAchete = entry.getValue();

            System.out.println("ID: " + produit.getIdProduit() +
                    ", Nom: " + produit.getNom() +
                    ", Prix unitaire: " + produit.getPrix() +
                    ", Quantité: " + quantiteAchete +
                    ", Montant partiel: " + (produit.getPrix() * quantiteAchete) + " DH");

            montantTotal += produit.getPrix() * quantiteAchete;
        }

        System.out.println("Montant total à payer : " + montantTotal + " DH");

        if (confirmerAchat) {
            System.out.println("Voulez-vous confirmer votre achat ? (true pour confirmer, false pour annuler)");
            boolean confirmation = scanner.nextBoolean();

            if (confirmation) {
                // Mettre à jour la base de données après confirmation d'achat
                for (Map.Entry<Produit, Integer> entry : produitsDansPanier.entrySet()) {
                    Produit produit = entry.getKey();
                    int quantiteAchete = entry.getValue();
                    produitDAO.mettreAJourQuantiteVendueEtTotale(produit, quantiteAchete);
                }

                // Afficher la quantité totale après l'achat
                //System.out.println("Quantité totale après l'achat : " + produitDAO.obtenirQuantiteTotale());
                System.out.println("Achat confirmé. Merci !");
            } else {
                System.out.println("Achat annulé. Confirmez votre achat s'il vous plaît.");
            }
        }
    }



    public Map<Produit, Integer> getProduitsDansPanier() {
        return produitsDansPanier;
    }






}
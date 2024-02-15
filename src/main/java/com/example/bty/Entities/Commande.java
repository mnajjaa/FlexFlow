package com.example.bty.Entities;

import java.util.Date;

public class Commande {
    private int idCommande;
    private Date dateCommande;
    private float MontantTotal;

    User membre ;



    public Commande(int idCommande, Date dateCommande, float montantTotal, User membre) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        MontantTotal = montantTotal;
        this.membre = membre;
    }

    public Commande(){}

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public float getMontantTotal() {
        return MontantTotal;
    }

    public void setMontantTotal(float montantTotal) {
        MontantTotal = montantTotal;
    }



    public User getMembre() {
        return membre;
    }

    public void setMembre(User membre) {
        this.membre = membre;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", dateCommande=" + dateCommande +
                ", MontantTotal=" + MontantTotal +
                ", id_user=" + membre +
                '}';
    }
}

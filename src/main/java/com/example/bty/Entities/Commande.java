package com.example.bty.Entities;

import java.util.Date;

public class Commande {
    private int idCommande;
    private Date dateCommande;
    private float MontantTotal;
    private String statut;
    private int id_user;

    public Commande(int idCommande, Date dateCommande, float montantTotal, String statut, int id_user) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        MontantTotal = montantTotal;
        this.statut = statut;
        this.id_user = id_user;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", dateCommande=" + dateCommande +
                ", MontantTotal=" + MontantTotal +
                ", statut='" + statut + '\'' +
                ", id_user=" + id_user +
                '}';
    }
}

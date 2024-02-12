package com.example.bty.Entities;

public class Produit {

    private int idProduit;
    private String nom;
    private String Description;
    private double Prix;
    private String Type;
    private int Quantite;

    private int quantiteVendues;

    public Produit (){}
    public Produit(int idProduit,String nom, String description, double prix, String type, int quantite , int quantiteVendues) {
        this.idProduit = idProduit ;
        this.nom = nom;
        Description = description;
        Prix = prix;
        Type = type;
        Quantite = quantite;
        this.quantiteVendues = quantiteVendues;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double prix) {
        Prix = prix;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public int getQuantiteVendues() {
        return quantiteVendues;
    }

    public void setQuantiteVendues(int quantiteVendues) {
        this.quantiteVendues = quantiteVendues;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nom='" + nom + '\'' +
                ", Description='" + Description + '\'' +
                ", Prix=" + Prix +
                ", Type='" + Type + '\'' +
                ", Quantite=" + Quantite +
                ", quantiteVendues=" + quantiteVendues +
                '}';
    }
}

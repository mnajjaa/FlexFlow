package com.example.bty.Entities;

public class Produit {
int id;
String nom;
String description;
float prix;
String type;
int quantite;

    public Produit() {
    }

    public Produit(int id, String nom, String description, float prix, String type, int quantite) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.type = type;
        this.quantite = quantite;
    }

    public Produit(String nom, String description, float prix, String type, int quantite) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.type = type;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", type='" + type + '\'' +
                ", quantite=" + quantite +
                '}';
    }
}

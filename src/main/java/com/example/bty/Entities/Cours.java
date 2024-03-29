package com.example.bty.Entities;

public class Cours {
    int id;
    String nom;
    String duree;
    String intensite;
    String cible;
    String categorie;
    String objectif;
    boolean etat;
    int nbr_participant;

    User coach;

    public Cours() {
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

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getIntensite() {
        return intensite;
    }

    public void setIntensite(String intensite) {
        this.intensite = intensite;
    }

    public String getCible() {
        return cible;
    }

    public void setCible(String cible) {
        this.cible = cible;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public int getNbr_participant() {
        return nbr_participant;
    }

    public void setNbr_participant(int nbr_participant) {
        this.nbr_participant = nbr_participant;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public Cours(String nom, String duree, String intensite, String cible, String categorie, String objectif, boolean etat, int nbr_participant, User coach) {
        this.nom = nom;
        this.duree = duree;
        this.intensite = intensite;
        this.cible = cible;
        this.categorie = categorie;
        this.objectif = objectif;
        this.etat = etat;
        this.nbr_participant = nbr_participant;
        this.coach = coach;
    }

    public Cours(int id, String nom, String duree, String intensite, String cible, String categorie, String objectif, boolean etat, int nbr_participant, User coach) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
        this.intensite = intensite;
        this.cible = cible;
        this.categorie = categorie;
        this.objectif = objectif;
        this.etat = etat;
        this.nbr_participant = nbr_participant;
        this.coach = coach;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", duree='" + duree + '\'' +
                ", intensite='" + intensite + '\'' +
                ", cible='" + cible + '\'' +
                ", categorie='" + categorie + '\'' +
                ", objectif='" + objectif + '\'' +
                ", etat=" + etat +
                ", nbr_participant=" + nbr_participant +
                ", coach=" + coach +
                '}';
    }
}

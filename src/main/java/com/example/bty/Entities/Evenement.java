package com.example.bty.Entities;

import java.util.Date;

public class Evenement {
    int id;
    String nom;
    Date date;
    int nbre_place;
    String moderateur;
    String categorie;
    String objectif;
    User coach;
    boolean etat;

    public Evenement() {
    }

    public Evenement(int id, String nom,Date date, int nbre_place, String moderateur, String categorie, String objectif, User coach, boolean etat) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.nbre_place = nbre_place;
        this.moderateur = moderateur;
        this.categorie = categorie;
        this.objectif = objectif;
        this.coach = coach;
        this.etat = etat;
    }

    public Evenement(String nom,Date date, int nbre_place, String moderateur, String categorie, String objectif, User coach, boolean etat) {
        this.nom = nom;
        this.date = date;
        this.nbre_place = nbre_place;
        this.moderateur = moderateur;
        this.categorie = categorie;
        this.objectif = objectif;
        this.coach = coach;
        this.etat = etat;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNbre_place() {
        return nbre_place;
    }

    public void setNbre_place(int nbre_place) {
        this.nbre_place = nbre_place;
    }

    public String getModerateur() {
        return moderateur;
    }

    public void setModerateur(String moderateur) {
        this.moderateur = moderateur;
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

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", date=" + date +
                ", nbre_place=" + nbre_place +
               // ", moderateur='" + moderateur + '\'' +
                ", categorie='" + categorie + '\'' +
                ", objectif='" + objectif + '\'' +
                ", coach=" + coach +
                ", etat=" + etat +
                '}';
    }
}

package com.example.bty.Entities;

import java.util.Date;

public class Reclamation {
    int id;
    Date date;
    String sujet;
    String description;
    User user;
    boolean etat;

    public Reclamation() {
    }

    public Reclamation(int id, Date date, String sujet, String description, User user, boolean etat) {
        this.id = id;
        this.date = date;
        this.sujet = sujet;
        this.description = description;
        this.user = user;
        this.etat = etat;
    }

    public Reclamation(Date date, String sujet, String description, User user, boolean etat) {
        this.date = date;
        this.sujet = sujet;
        this.description = description;
        this.user = user;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", date=" + date +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", etat=" + etat +
                '}';
    }

}

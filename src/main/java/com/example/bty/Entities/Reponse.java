package com.example.bty.Entities;

import java.util.Date;

public class Reponse {
    int id;
    String reponse;
    Date date_reponse;
    Reclamation reclamation;

    public Reponse() {
    }

    public Reponse(int id, String reponse, Date date_reponse,Reclamation reclamation) {
        this.id = id;
        this.date_reponse = date_reponse;
        this.reponse = reponse;
        this.reclamation = reclamation;
    }

    public Reponse(String reponse,Date  date_reponse, Reclamation reclamation) {
        this.reponse = reponse;
        this.date_reponse = date_reponse;
        this.reclamation = reclamation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(Date date_reponse) {
        this.date_reponse = date_reponse;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }


    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reponse='" + reponse + '\'' +
                ", reclamation=" + reclamation +
                '}';
    }
}

package com.example.bty.Entities;

public class Reponse {
    int id;
    String reponse_reclamation;
    Reclamation reclamation;

    public Reponse() {
    }

    public Reponse(int id, String reponse_reclamation, Reclamation reclamation) {
        this.id = id;
        this.reponse_reclamation = reponse_reclamation;
        this.reclamation = reclamation;
    }

    public Reponse(String reponse_reclamation, Reclamation reclamation) {
        this.reponse_reclamation = reponse_reclamation;
        this.reclamation = reclamation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getReponse_reclamation() {
        return reponse_reclamation;
    }

    public void setReponse_reclamation(String reponse_reclamation) {
        this.reponse_reclamation = reponse_reclamation;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reponse_reclamation='" + reponse_reclamation + '\'' +
                ", reclamation=" + reclamation +
                '}';
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }


    public Reponse(String reponse_reclamation) {
        this.reponse_reclamation = reponse_reclamation;
    }
}



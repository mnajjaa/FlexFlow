package com.example.bty.Entities;

public class Offre {
    private int id;
    private Specialite  specialite;
    private float tarif_heure;
    private User coach;


    public Offre() {
        this.specialite = specialite;
        this.tarif_heure = tarif_heure;
        this.coach = coach;
    }

    public Offre(int id, Specialite specialite, float tarif_heure, User coach) {
        this.id = id;
        this.specialite = specialite;
        this.tarif_heure = tarif_heure;
        this.coach = coach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Specialite getspecialite() {
        return specialite;
    }

    public void setspecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public float getTarif_heure() {
        return  tarif_heure;
    }

    public void setTarif_heure(float tarif_heure) {
        this.tarif_heure = tarif_heure;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    @Override
    public String toString() {
        return "Specalite{" +
                "id=" + id +
                ", specialite='" + specialite + '\'' +
                ", tarif_heure=" + tarif_heure +
                ", coach=" + coach +
                '}';
    }
}
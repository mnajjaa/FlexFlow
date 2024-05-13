package com.example.bty.Entities;

import com.example.bty.Services.ServiceOffre;


public class Offre {
    private String nom;
    private int id;
    private Specialite specialite;
    private float tarif_heure;
    private User coach;
    private Etat etat_offre;
    private String email;


//private byte[] image;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Offre(int id, String specialite, float tarifHeure, int idCoach, String etat_offre,String email) {

        this.specialite = this.specialite;
        this.tarif_heure = tarif_heure;
        this.coach = coach;
        this.email = email;

    }

    public Offre(int id, String nom,Specialite specialite, float tarif_heure, User coach,String email) {
        this.nom=nom;
        this.id = id;
        this.specialite = specialite;
        this.tarif_heure = tarif_heure;
        this.coach = coach;
        this.email = email;
        //this.image=image;
    }

    public Offre() {

    }

    public int getId() {
        return id;
    }

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }

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
        return tarif_heure;
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

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Etat getEtat_offre() {
        return etat_offre;
    }

    public void setEtat_offre(Etat etat_offre) {
        this.etat_offre = etat_offre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Specalite{" +
                "id=" + id +
                ", specialite='" + specialite + '\'' +
                ", tarif_heure=" + tarif_heure +
                ", coach=" + coach +
                ", email=" + email +
                '}';
    }

}

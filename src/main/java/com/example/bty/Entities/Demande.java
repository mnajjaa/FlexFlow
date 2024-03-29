package com.example.bty.Entities;

public class Demande {
    int id;
    int age;
    int nbre_heure;
    boolean maladie_chronique;
    String but;
    String niveau_physique;
    User membre;
    Offre offre;

    public Demande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNbre_heure() {
        return nbre_heure;
    }

    public void setNbre_heure(int nbre_heure) {
        this.nbre_heure = nbre_heure;
    }

    public boolean isMaladie_chronique() {
        return maladie_chronique;
    }

    public void setMaladie_chronique(boolean maladie_chronique) {
        this.maladie_chronique = maladie_chronique;
    }

    public String getBut() {
        return but;
    }

    public void setBut(String but) {
        this.but = but;
    }

    public String getNiveau_physique() {
        return niveau_physique;
    }

    public void setNiveau_physique(String niveau_physique) {
        this.niveau_physique = niveau_physique;
    }

    public User getMembre() {
        return membre;
    }

    public void setMembre(User membre) {
        this.membre = membre;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Demande(int id, int age, int nbre_heure, boolean maladie_chronique, String but, String niveau_physique, User membre, Offre offre) {
        this.id = id;
        this.age = age;
        this.nbre_heure = nbre_heure;
        this.maladie_chronique = maladie_chronique;
        this.but = but;
        this.niveau_physique = niveau_physique;
        this.membre = membre;
        this.offre = offre;
    }

    public Demande(int age, int nbre_heure, boolean maladie_chronique, String but, String niveau_physique, User membre, Offre offre) {
        this.age = age;
        this.nbre_heure = nbre_heure;
        this.maladie_chronique = maladie_chronique;
        this.but = but;
        this.niveau_physique = niveau_physique;
        this.membre = membre;
        this.offre = offre;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", age=" + age +
                ", nbre_heure=" + nbre_heure +
                ", maladie_chronique=" + maladie_chronique +
                ", but='" + but + '\'' +
                ", niveau_physique='" + niveau_physique + '\'' +
                ", membre=" + membre +
                ", offre=" + offre +
                '}';
    }
}

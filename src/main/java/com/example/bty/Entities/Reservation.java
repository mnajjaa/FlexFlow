package com.example.bty.Entities;

import java.util.Date;

public class Reservation {
    int id;
    Date date_reservation;
    int nbre_places;
    Evenement evenement;
    User membre;


    public Reservation() {
    }

    public Reservation(int id,Date date_reservation,int nbre_places, Evenement evenement, User membre) {
        this.id = id;
        this.date_reservation = date_reservation;
        this.nbre_places = nbre_places;
        this.evenement = evenement;
        this.membre = membre;
    }

    public Reservation(Date date_reservation, int nbre_places, Evenement evenement, User membre) {
        this.date_reservation = date_reservation;
        this.nbre_places = nbre_places;
        this.evenement = evenement;
        this.membre = membre;
    }

    public int getId() {
        return id;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public int getNbre_places() {
        return nbre_places;
    }

    public void setNbre_places(int nbre_places) {
        this.nbre_places = nbre_places;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public User getMembre() {
        return membre;
    }

    public void setMembre(User membre) {
        this.membre = membre;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date_reservation=" + date_reservation +
                ", nbre_places=" + nbre_places +
                ", evenement=" + evenement +
                ", membre=" + membre +
                '}';
    }
}

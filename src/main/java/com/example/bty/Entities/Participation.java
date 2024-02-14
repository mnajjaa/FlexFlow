package com.example.bty.Entities;

public class Participation {
    int id;
    User user;
    Cours cours;


    public Participation() {
    }

    public Participation(int id, User user, Cours cours) {
        this.id = id;
        this.user = user;
        this.cours = cours;

    }

    public Participation(User user, Cours cours) {
        this.user = user;
        this.cours = cours;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cours getcours() {
        return cours;
    }

    public void setcours(Cours cours) {
        this.cours = cours;
    }
}

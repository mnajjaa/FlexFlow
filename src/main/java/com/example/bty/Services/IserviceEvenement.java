package com.example.bty.Services;

import com.example.bty.Entities.Evenement;

import java.util.List;

public interface IserviceEvenement <E> {
    public void ajouterEvenement (E e );
    public void modifierEvenement(E e);
    public void supprimerEvenement(int id);
    public List<E> consulterEvenements();
}

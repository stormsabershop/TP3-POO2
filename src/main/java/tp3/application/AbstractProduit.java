package tp3.application;

import tp3.echange.Descriptible;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractProduit implements Descriptible, Cloneable, Serializable {
    private String nom;
    private int numSerie; // unique pour chaque produit
    private int numProduit; // associer au type de produit

    private LocalDateTime date = LocalDateTime.now();//la date de création
    private int prix;

    private static int numSerieCompteur = 1;
    private static int numProduitCompteur =0;

    public AbstractProduit(String nom, int prix) {
        this.nom = nom;
        this.prix = prix;
        this.numProduit = numProduitCompteur++;
        this.numSerie = numSerieCompteur++;
    }



    @Override
    public String toString() {
        return "AbstractProduit{" +
                "nom='" + nom + '\'' +
                ", numSerie=" + numSerie +
                ", numProduit=" + numProduit +
                ", date=" + date +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object retObject = super.clone();
        this.numSerie = numSerieCompteur++;
        this.date = LocalDateTime.now();
        return retObject;
    }

    public int getNumProduit() {
        return numProduit;
    }

    public String getNom() {
        return nom;
    }


    public int getNumSerie() {
        return numSerie;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public abstract int getNumeroCategorie();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractProduit that)) return false;
        return numSerie == that.numSerie;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numSerie);
    }

    public int getPrix() {
        return prix;
    }
}

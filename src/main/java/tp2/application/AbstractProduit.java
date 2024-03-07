package tp2.application;

import tp2.echange.Descriptible;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class AbstractProduit implements Descriptible, Cloneable, Serializable {
    private String nom;
    private int numSerie; // unique pour chaque produit
    private int numProduit; // associer au type de produit

    private LocalDateTime date = LocalDateTime.now();//la date de création

    private static int numSerieCompteur = 1;
    private static int numProduitCompteur =0;

    public AbstractProduit(String nom) {
        this.nom = nom;
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

}

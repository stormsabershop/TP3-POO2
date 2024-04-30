package tp3.etudiant.boite;

import tp3.application.AbstractProduit;
import tp3.echange.Descriptible;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Contient max 10 produits
public class Boite implements Descriptible, Serializable {

    private static int dernierNumeroBoite = 0;

    public final static int NOMBRE_ELEMENT_MAX = 10;

    private int numeroLivraison;
    private int numeroEmballage;

    private boolean estOuverte;

    private List<AbstractProduit> contenu;

    public Boite(int taille, int numeroLivraison) {


        this.estOuverte = false;

        this.numeroLivraison = numeroLivraison;
        this.numeroEmballage = dernierNumeroBoite++;

        this.contenu = new ArrayList<>();
    }


    public int getNumeroLivraison() {
        return numeroLivraison;
    }

    public void setNumeroLivraison(int numeroLivraison) {
        this.numeroLivraison = numeroLivraison;
    }

    public int getNumeroEmballage() {
        return numeroEmballage;
    }

    public void setNumeroEmballage(int numeroEmballage) {
        this.numeroEmballage = numeroEmballage;
    }

    public boolean isEstOuverte() {
        return estOuverte;
    }

    public void fermeBoite() {
        this.estOuverte = false;
    }

    public List<AbstractProduit> getContenu() {
        return contenu;
    }


    public boolean ajouteProduit(AbstractProduit produit) {
        assert produit != null;

        return contenu.add(produit);
    }

    public int getNumeroProduit() {
        int retNumeroProduit = -1;
        if (contenu.size() > 0) {
            retNumeroProduit = contenu.get(0).getNumProduit();
        }
        return retNumeroProduit;
    }


    public int getNumeroCategorie() {
        int retNumeroProduit = -1;
        if (contenu.size() > 0) {
            retNumeroProduit = contenu.get(0).getNumeroCategorie();
        }
        return retNumeroProduit;
    }

    @Override
    public String toString() {
        return "Boite{" +
                "num produit=" + getNumeroProduit() +
                ", numeroEmballage=" + numeroEmballage +
                ", estOuverte=" + estOuverte +
                ", quantité=" + contenu.size() +
                ", type=" + (contenu.size() > 0 ? contenu.get(0).getNumProduit() : -1) +
                '}';
    }

    public void ouvre() {
        estOuverte = true;
    }

    @Override
    public String decrit() {
        return "boite-" + numeroEmballage + " #prod:" + getNumeroProduit()
                + " - contient:" + getContenu().size() + " produits";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boite boite)) return false;
        return numeroEmballage == boite.numeroEmballage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroEmballage);
    }

    public Boite(AbstractProduit produit) {
        this.contenu = new ArrayList<>();
        contenu.add(produit);
    }
}

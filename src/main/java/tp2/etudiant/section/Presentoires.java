package tp2.etudiant.section;

import tp2.application.AbstractProduit;
import tp2.echange.Descriptible;
import tp2.etudiant.boite.Boite;

import java.util.ArrayList;
import java.util.Collection;

public class Presentoires implements Descriptible {
    Collection<AbstractProduit> produits = new ArrayList<>();

    public Presentoires() {
        this.produits = produits;
    }

    @Override
    public String decrit() {
        if (produits.isEmpty()) {
            return toString();
        } else return "Présentoires: \n";

    }

    @Override
    public String toString() {
        return "Presentoires:\n" +
                "(vide)";
    }
}

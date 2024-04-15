package tp2.etudiant.section;

import tp2.application.AbstractProduit;
import tp2.echange.Descriptible;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Charite implements Descriptible, ChariteI, Lists {

    public void donneProduits(Collection<AbstractProduit> produits) {
        produitsListCharite.addAll(produits);
    }

    public void donneProduit(AbstractProduit produit) {
        produitsListCharite.add(produit);
    }

    @Override
    public String decrit() {
        return "ici les produits donnés en charités:\n" + toString();
    }

    @Override
    public String toString() {
        return "Charite{" +
                "produitsListCharite=" + produitsListCharite +
                '}';
    }
}

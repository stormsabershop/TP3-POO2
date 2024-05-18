package tp3.etudiant.section;

import tp3.application.AbstractProduit;
import tp3.echange.Descriptible;

import java.io.Serializable;
import java.util.Collection;

public class Charite implements Descriptible, ChariteI, Lists, Serializable {

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

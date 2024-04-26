package tp3.etudiant.section;

import tp3.application.AbstractProduit;
import tp3.etudiant.boite.Boite;

import java.util.ArrayList;
import java.util.Collection;

public class AiresDesPresentoires implements AireI, Lists {

    Collection<AbstractProduit> produitsList;

    public AiresDesPresentoires() {
        this.produitsList = new ArrayList<>();
    }

    @Override
    public String decrit() {
        return "aire des presentoires";
    }

    @Override
    public Collection<AbstractProduit> retireProduits(Collection<AbstractProduit> items) {
        produitsList.removeAll(items);
        return produitsList;
    }

    @Override
    public Collection<AbstractProduit> getAllProduits() {
        return produitsList;
    }

    @Override
    public Collection<AbstractProduit> placerProduits(Boite produits) {
        produitsList.addAll(produits.getContenu());
        return produitsList;
    }

    @Override
    public boolean placerProduits(Collection<AbstractProduit> produits) {
        return produitsList.addAll(produits);
    }

    @Override
    public void viderAire() {
        produitsList.clear();

    }

    @Override
    public void gereSurplus(Collection<AbstractProduit> restant) {
        ((ChariteI) produitsListCharite).donneProduits(restant);
    }
}

package tp2.etudiant.section;

import tp2.application.AbstractProduit;
import tp2.etudiant.boite.Boite;

import java.util.ArrayList;
import java.util.Collection;

public class Vrac implements AireI{

    @Override
    public String decrit() {
        return "vrac ici";
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
        return false;
    }

    @Override
    public void viderAire() {
        produitsList.clear();

    }

    @Override
    public void gereSurplus(Collection<AbstractProduit> restant) {

    }
}
